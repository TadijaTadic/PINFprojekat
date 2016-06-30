INSERT INTO [banka].[dbo].[DRZAVA]
           ([DR_SIFRA]
           ,[DR_NAZIV])
     VALUES
           (1
           ,'Srbija'),
           (2
           ,'Crna Gora')
GO

INSERT INTO [banka].[dbo].[NASELJENO_MESTO]
           ([NM_SIFRA]
           ,[DR_SIFRA]
           ,[NM_NAZIV]
           ,[NM_PTTOZNAKA])
     VALUES
           (1
           ,1
           ,'Novi Sad'
           ,'21000'),
           (2
           ,1
           ,'Beograd'
           ,'11000')        
GO

INSERT INTO [banka].[dbo].[VALUTE]
           ([ID_VALUTE]
           ,[DR_SIFRA]
           ,[VA_IFRA]
           ,[VA_NAZIV]
           ,[VA_DOMICILNA])
     VALUES
           (1
           ,1
           ,1
           ,'RSD'
           ,1)
GO

INSERT INTO [banka].[dbo].[BANKA]
           ([ID_BANKE]
           ,[SIFRA_BANKE]
           ,[PR_PIB]
           ,[PR_NAZIV]
           ,[PR_ADRESA]
           ,[PR_EMAIL]
           ,[PR_WEB]
           ,[PR_TELEFON]
           ,[PR_FAX]
           ,[PR_BANKA])
     VALUES
           (1
           ,1
           ,'123'
           ,'Intesa Banca'
           ,'Neznanog junaka 1'
           ,'mail@domen.com'
           ,'www.intesa.com'
           ,'011600600'
           ,'011600600'
           ,0)
GO

INSERT INTO [banka].[dbo].[FIZICKO_LICE]
           ([ID_KLIJENTA]
           ,[JMBG]
           ,[ADRESA]
           ,[TELEFON]
           ,[E_MAIL]
           ,[PREZIME]
           ,[IME])
     VALUES
           (1
           ,0208992830123
           ,'Neznanog junaka bb'
           ,063123456
           ,'mail@mail.com'
           ,'Markovic'
           ,'Marko')
GO

INSERT INTO [banka].[dbo].[PRAVNO_LICE]
           ([ID_KLIJENTA]
           ,[PIB]
           ,[ADRESA]
           ,[TELEFON]
           ,[E_MAIL]
           ,[NAZIV_]
           ,[WEB_ADRESA]
           ,[FAX]
           ,[IME]
           ,[PREZIME]
           ,[JMBG_LICA])
     VALUES
           (2
           ,1234567890
           ,'Neznanog junaka 3'
           ,021100100
           ,'mail@mail.com'
           ,'Preduzece'
           ,'www.sajt.com'
           ,021100100
           ,'Janko'
           ,'Jankovic'
           ,123456789012)
GO

INSERT INTO [banka].[dbo].[RACUNI_PRAVNIH_LICA]
           ([ID_RACUNA]
           ,[ID_BANKE]
           ,[ID_VALUTE]
           ,[ID_KLIJENTA]
           ,[BAR_RACUN]
           ,[BAR_DATOTV]
           ,[BAR_VAZI])
     VALUES
           (1
           ,1
           ,1
           ,1
           ,123-123456-12
           ,2016-06-06
           ,1),
           (2
           ,1
           ,1
           ,2
           ,123-654321-12
           ,2016-06-06
           ,1)
GO

INSERT INTO [banka].[dbo].[DNEVNO_STANJE_RACUNA]
           ([DSR_IZVOD]
           ,[ID_RACUNA]
           ,[DSR_DATUM]
           ,[DSR_PRETHODNO]
           ,[DSR_UKORIST]
           ,[DSR_NATERET]
           ,[DSR_NOVOSTANJE])
     VALUES
           (1
           ,1
           ,2016-06-06
           ,0
           ,0
           ,0
           ,0),
           (2
           ,2
           ,2016-06-06
           ,0
           ,0
           ,0
           ,0)
GO













