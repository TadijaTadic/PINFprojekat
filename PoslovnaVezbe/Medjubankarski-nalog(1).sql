CREATE PROCEDURE Medjubank_nalog
        @RacPoverioca varchar(18),
		@RacDuznika varchar (18),
		@Iznos  decimal(15,2),
		@DatPrijema DATETIME,
		@RTGS  BIT
  AS
  DECLARE
       @IdNaloga varchar(20),
       @IdBankePoverioca varchar(18),
	   @IdBankeDuznika varchar(18),   
	  /* @DatUnalogu DATETIME,*/
	   @Upper INT = 0000,
       @Lower INT = 9999,
	   @postojiPoverioc varchar(20),
	   @postojiDuznik  varchar(20),
	   @postojiDatum   varchar(20) 
	  /* SELECT
	      @DatUnalogu = DATUM FROM MEDJUBANKARSKI_NALOG WHERE DATUM = @DatPrijema*/
       IF EXISTS(SELECT DATUM FROM MEDJUBANKARSKI_NALOG  WHERE DATUM = @DatPrijema)
	   BEGIN 
         SET  @postojiDatum = 'true'
		END
	   ELSE
	   BEGIN 
	     SET  @postojiDatum = 'false'
	   END 

	   SELECT 
	      @IdBankePoverioca = ID_BANKE FROM RACUNI_PRAVNIH_LICA WHERE BAR_RACUN = @RacPoverioca
	   SELECT
		  @IdBankeDuznika = ID_BANKE FROM RACUNI_PRAVNIH_LICA WHERE BAR_RACUN = @RacDuznika
	   
	   SELECT @IdNaloga = ROUND(((@Upper - @Lower -1) * RAND() + @Lower), 0)

	   IF EXISTS(SELECT * FROM ANALITIKA_IZVODA WHERE ASI_RACPOV = @RacPoverioca)
	   BEGIN 
         SET  @postojiPoverioc = 'true'
		END
	   ELSE
	   BEGIN 
	     SET @postojiPoverioc = 'false'
	   END
	   IF EXISTS(SELECT * FROM ANALITIKA_IZVODA WHERE ASI_RACDUZ = @RacDuznika)
	   BEGIN 
         SET @postojiDuznik = 'true'
		END
	   ELSE
	   BEGIN 
	     SET @postojiDuznik = 'false'
	   END
	   BEGIN TRANSACTION
	   IF(@IdBankePoverioca != @IdBankeDuznika AND @postojiDatum = 'false' /*AND @postojiPoverioc = 'true' *//*AND  @DatUnalogu != @DatPrijema*/ )
	          INSERT INTO  MEDJUBANKARSKI_NALOG VALUES(@IdNaloga,@IdBankePoverioca,@IdBankeDuznika,@Iznos,@DatPrijema,@RTGS,'1')
       IF(@IdBankePoverioca != @IdBankeDuznika AND @postojiDatum = 'true'/*@DatUnalogu = @DatPrijema*/ AND @postojiPoverioc = 'true' AND @postojiDuznik = 'true' AND @RTGS = 0 )
	          UPDATE MEDJUBANKARSKI_NALOG
			  SET
			     UKUPAN_IZNOS = UKUPAN_IZNOS + @Iznos
              WHERE 
			     DATUM = @DatPrijema
	  IF(@IdBankePoverioca != @IdBankeDuznika AND @postojiDatum = 'true' /*@DatUnalogu = @DatPrijema*/ AND @postojiPoverioc = 'true' AND @postojiDuznik = 'true' AND @RTGS = 1 )
	          INSERT INTO  MEDJUBANKARSKI_NALOG VALUES(@IdNaloga,@IdBankePoverioca,@IdBankeDuznika,@Iznos,@DatPrijema,@RTGS,'1')
   IF @@ERROR <> 0
   BEGIN
      ROLLBACK TRANSACTION
      RETURN
   END
   COMMIT TRANSACTION   
GO     