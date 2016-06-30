CREATE PROCEDURE Ukid
     @Idracuna varchar(20),
	 @NaRacun varchar(18)
  AS
  BEGIN TRANSACTION 
       UPDATE RACUNI_PRAVNIH_LICA
	  SET BAR_VAZI = 0
	  where ID_RACUNA = @Idracuna
	  EXEC TabelaUkid @Idracuna, @NaRacun
   IF @@ERROR <> 0
   BEGIN
      ROLLBACK TRANSACTION
      RETURN
   END
   COMMIT TRANSACTION 
   GO      
   
