CREATE PROCEDURE TabelaUkid
    @IdRacuna varchar(20),
    @NaRacun varchar(18)
  AS
  DECLARE
     @IdUkid  varchar(4),
	 
      @DatUk DATETIME,
	  
	  @Upper INT = 0000,
      @Lower INT = 9999
   
    SELECT @IdUkid = ROUND(((@Upper - @Lower -1) * RAND() + @Lower), 0)
	
	SET  @DatUk = GETDATE()
	
  BEGIN TRANSACTION 
     INSERT INTO UKIDANJE VALUES (@IdUkid,@IdRacuna,@DatUk,@NaRacun)
	 IF @@ERROR <> 0
   BEGIN
      ROLLBACK TRANSACTION
      RETURN
   END
   COMMIT TRANSACTION   
GO     