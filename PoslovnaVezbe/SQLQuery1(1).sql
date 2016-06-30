CREATE PROCEDURE Ukid
     @Idracuna varchar(20),
	 @NaRacun varchar(18)
  AS
  DECLARE 
     @vazi BIT

	 SELECT @vazi = BAR_VAZI FROM RACUNI_PRAVNIH_LICA WHERE ID_RACUNA = @Idracuna
  BEGIN TRANSACTION 
     IF(@vazi = 1)
   BEGIN
       UPDATE RACUNI_PRAVNIH_LICA
	  SET BAR_VAZI = 0
	  where ID_RACUNA = @Idracuna
	  EXEC TabelaUkid @Idracuna, @NaRacun
   END
   IF @@ERROR <> 0
   BEGIN
      ROLLBACK TRANSACTION
      RETURN
   END
   COMMIT TRANSACTION 
   GO      
   
