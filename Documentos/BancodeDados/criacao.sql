spool on
spool "C:\temp\criacao.log"

prompt ==================================================
prompt CREATE TABLE TBL_BANCO
prompt ==================================================
CREATE TABLE TBL_BANCO 
    ( 
     ID NUMBER  NOT NULL , 
     NOME VARCHAR2 (100)  NOT NULL 
    ) 
    LOGGING 
;


prompt ==================================================
prompt CREATE PRIMARY KEY TBL_BANCO
prompt ==================================================
ALTER TABLE TBL_BANCO 
    ADD CONSTRAINT PK_BANCO PRIMARY KEY ( ID ) ;


prompt ==================================================
prompt CREATE TABLE TBL_CATEGORIA
prompt ==================================================
CREATE TABLE TBL_CATEGORIA 
    ( 
     ID NUMBER  NOT NULL , 
     NOME VARCHAR2 (50)  NOT NULL , 
     ID_GRUPO_CATEGORIA NUMBER  NOT NULL 
    ) 
    LOGGING 
;


prompt ==================================================
prompt CREATE PRIMARY KEY TBL_CATEGORIA
prompt ==================================================
ALTER TABLE TBL_CATEGORIA 
    ADD CONSTRAINT PK_CATEGORIA PRIMARY KEY ( ID ) ;


prompt ==================================================
prompt CREATE TABLE TBL_CLASSIFICA_AUTO
prompt ==================================================
CREATE TABLE TBL_CLASSIFICA_AUTO 
    ( 
     ID NUMBER  NOT NULL , 
     EXPRESSAO VARCHAR2 (100)  NOT NULL , 
     ID_CATEGORIA NUMBER  NOT NULL 
    ) 
    LOGGING 
;


prompt ==================================================
prompt CREATE PRIMARY KEY TBL_CLASSIFICA_AUTO
prompt ==================================================
ALTER TABLE TBL_CLASSIFICA_AUTO 
    ADD CONSTRAINT PK_CLASSIFIC_AUTO PRIMARY KEY ( ID ) ;


prompt ==================================================
prompt CREATE TABLE TBL_CONTA
prompt ==================================================
CREATE TABLE TBL_CONTA 
    ( 
	  ID            NUMBER not null,
	  NOME          VARCHAR2(50) not null,
	  SALDO_INICIAL NUMBER(19,2) default 0 null,
	  ID_USUARIO    NUMBER not null,
	  ID_BANCO      NUMBER,
	  DATA_VALIDADE DATE,
	  LIMITE_CARTAO NUMBER(19,2),
	  ID_TIPO_CONTA NUMBER not null
    ) 
    LOGGING 
;

prompt ==================================================
prompt CREATE PRIMARY KEY TBL_CONTA
prompt ==================================================
ALTER TABLE TBL_CONTA 
    ADD CONSTRAINT PK_CONTA PRIMARY KEY ( ID ) ;


prompt ==================================================
prompt CREATE TABLE TBL_CONTA_PAGAR_RECEBER
prompt ==================================================
CREATE TABLE TBL_CONTA_PAGAR_RECEBER 
    ( 
     ID NUMBER  NOT NULL , 
     NUMERO_DOCUMENTO NUMBER , 
     DESCRICAO VARCHAR2 (100)  NOT NULL , 
     VALOR NUMBER(19,2) , 
     ID_USUARIO NUMBER  NOT NULL , 
     ID_CATEGORIA NUMBER , 
     ID_TIPO_MOVIMENTO NUMBER  NOT NULL , 
     DATA_MOVIMENTO DATE  NOT NULL , 
     PAGO VARCHAR2 (1)  NOT NULL 
    ) 
    LOGGING 
;


prompt ==================================================
prompt CREATE PRIMARY KEY TBL_CONTA_PAGAR_RECEBER
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER 
    ADD CONSTRAINT PK_CONTAS PRIMARY KEY ( ID ) ;


prompt ==================================================
prompt CREATE TABLE TBL_GRUPO_CATEGORIA
prompt ==================================================
CREATE TABLE TBL_GRUPO_CATEGORIA 
    ( 
     ID NUMBER  NOT NULL , 
     GRUPO VARCHAR2 (50)  NOT NULL , 
     ID_TIPO_MOVIMENTO NUMBER  NOT NULL 
    ) 
    LOGGING 
;


prompt ==================================================
prompt CREATE PRIMARY KEY TBL_GRUPO_CATEGORIA
prompt ==================================================
ALTER TABLE TBL_GRUPO_CATEGORIA 
    ADD CONSTRAINT PK_GRUPO_CATEGORIA PRIMARY KEY ( ID ) ;


prompt ==================================================
prompt CREATE TABLE TBL_MOVIMENTO
prompt ==================================================
CREATE TABLE TBL_MOVIMENTO 
    ( 
     ID NUMBER  NOT NULL , 
     NUMERO_DOCUMENTO NUMBER , 
     DESCRICAO VARCHAR2 (100)  NOT NULL , 
     VALOR NUMBER  NOT NULL , 
     ID_USUARIO NUMBER  NOT NULL , 
     ID_CONTA NUMBER  NOT NULL , 
     ID_CATEGORIA NUMBER, 
     ID_TIPO_MOVIMENTO NUMBER  NOT NULL , 
     DATA_MOVIMENTO DATE  NOT NULL 
    ) 
    LOGGING 
;


prompt ==================================================
prompt CREATE PRIMARY KEY TBL_CLASSIFICA_AUTO
prompt ==================================================
ALTER TABLE TBL_MOVIMENTO 
    ADD CONSTRAINT PK_MOVIMENTO PRIMARY KEY ( ID ) ;


prompt ==================================================
prompt CREATE TABLE TBL_SALDO
prompt ==================================================
CREATE TABLE TBL_SALDO 
    ( 
     ID NUMBER  NOT NULL , 
     SALDO NUMBER  NOT NULL , 
     ID_USUARIO NUMBER  NOT NULL 
    ) 
    LOGGING 
;


prompt ==================================================
prompt CREATE PRIMARY KEY TBL_CLASSIFICA_AUTO
prompt ==================================================
ALTER TABLE TBL_SALDO 
    ADD CONSTRAINT PK_SALDO PRIMARY KEY ( ID ) ;


prompt ==================================================
prompt CREATE TABLE TBL_TIPO_CONTA
prompt ==================================================
CREATE TABLE TBL_TIPO_CONTA 
    ( 
     ID NUMBER  NOT NULL , 
     NOME VARCHAR2 (100)  NOT NULL 
    ) 
    LOGGING 
;


prompt ==================================================
prompt CREATE PRIMARY KEY TBL_CLASSIFICA_AUTO
prompt ==================================================
ALTER TABLE TBL_TIPO_CONTA 
    ADD CONSTRAINT PK_TIPO_CONTA PRIMARY KEY ( ID ) ;


prompt ==================================================
prompt CREATE TABLE TBL_TIPO_MOVIMENTO
prompt ==================================================
CREATE TABLE TBL_TIPO_MOVIMENTO 
    ( 
     ID NUMBER  NOT NULL , 
     NOME VARCHAR2 (50)  NOT NULL 
    ) 
    LOGGING 
;


prompt ==================================================
prompt CREATE PRIMARY KEY TBL_CLASSIFICA_AUTO
prompt ==================================================
ALTER TABLE TBL_TIPO_MOVIMENTO 
    ADD CONSTRAINT PK_TIPO_MOVIMENTO PRIMARY KEY ( ID ) ;


prompt ==================================================
prompt CREATE TABLE TBL_USUARIO
prompt ==================================================
CREATE TABLE TBL_USUARIO 
    ( 
     ID NUMBER  NOT NULL , 
     NOME VARCHAR2 (100)  NOT NULL , 
     PASSWORD VARCHAR2 (100)  NOT NULL , 
     USERNAME VARCHAR2 (100)  NOT NULL , 
     EMAIL VARCHAR2 (50)  NOT NULL 
    ) 
        TABLESPACE TCC_PROJETO_DATA 
        LOGGING 
;



COMMENT ON COLUMN TBL_USUARIO.ID IS 'ID UNICO DO USUARIO' 
;

COMMENT ON COLUMN TBL_USUARIO.NOME IS 'NOME DO USUARIO' 
;

COMMENT ON COLUMN TBL_USUARIO.PASSWORD IS 'SENHA DO USUARIO' 
;

COMMENT ON COLUMN TBL_USUARIO.USERNAME IS 'LOGIN DO USUARIO' 
;

prompt ==================================================
prompt CREATE PRIMARY KEY TBL_USUARIO
prompt ==================================================
ALTER TABLE TBL_USUARIO 
    ADD CONSTRAINT PK_USUARIO PRIMARY KEY ( ID ) ;

prompt ==================================================
prompt CREATE UNIQUE KEY TBL_USUARIO
prompt ==================================================
ALTER TABLE TBL_USUARIO ADD CONSTRAINT AK_USERNAME UNIQUE (USERNAME);

prompt ==================================================
prompt ALTER TABLE TBL_CLASSIFICA_AUTO ADD PRIMEIRA_VEZ
prompt ==================================================
ALTER TABLE TBL_USUARIO ADD PRIMEIRA_VEZ VARCHAR(1) NOT NULL;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_CATEGORIA REF TBL_GRUPO_CATEGORIA
prompt ==================================================
ALTER TABLE TBL_CATEGORIA 
    ADD CONSTRAINT FK_CATEGORIA_GRUPO FOREIGN KEY (ID_GRUPO_CATEGORIA) 
    REFERENCES TBL_GRUPO_CATEGORIA (ID) NOT DEFERRABLE;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_CLASSIFICA_AUTO REF TBL_CATEGORIA
prompt ==================================================
ALTER TABLE TBL_CLASSIFICA_AUTO 
    ADD CONSTRAINT FK_CLASS_AUTO_CATEGORIA FOREIGN KEY (ID_CATEGORIA) 
    REFERENCES TBL_CATEGORIA (ID) NOT DEFERRABLE;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_CONTA REF TBL_BANCO
prompt ==================================================
ALTER TABLE TBL_CONTA 
    ADD CONSTRAINT FK_CONTA_BANCO FOREIGN KEY 
    ( 
     ID_BANCO
    ) 
    REFERENCES TBL_BANCO 
    ( 
     ID
    ) 
    NOT DEFERRABLE 
;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_CONTA REF TBL_TIPO_CONTA
prompt ==================================================
ALTER TABLE TBL_CONTA 
    ADD CONSTRAINT FK_CONTA_TIPO_CONTA FOREIGN KEY 
    ( 
     ID_TIPO_CONTA
    ) 
    REFERENCES TBL_TIPO_CONTA 
    ( 
     ID
    ) 
    NOT DEFERRABLE 
;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_CONTA REF TBL_USUARIO
prompt ==================================================
ALTER TABLE TBL_CONTA 
    ADD CONSTRAINT FK_CONTA_USUARIO FOREIGN KEY 
    ( 
     ID_USUARIO
    ) 
    REFERENCES TBL_USUARIO 
    ( 
     ID
    ) 
    ON DELETE CASCADE 
    NOT DEFERRABLE 
;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_GRUPO_CATEGORIA REF TBL_TIPO_MOVIMENTO
prompt ==================================================
ALTER TABLE TBL_GRUPO_CATEGORIA 
    ADD CONSTRAINT FK_GRP_CAT_TP_MOV FOREIGN KEY 
    ( 
     ID_TIPO_MOVIMENTO
    ) 
    REFERENCES TBL_TIPO_MOVIMENTO 
    ( 
     ID
    ) 
    NOT DEFERRABLE 
;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_MOVIMENTO REF TBL_TIPO_MOVIMENTO
prompt ==================================================
ALTER TABLE TBL_MOVIMENTO 
    ADD CONSTRAINT FK_MOVIMENTO_TIPO_MOVIMENTO FOREIGN KEY 
    ( 
     ID_TIPO_MOVIMENTO
    ) 
    REFERENCES TBL_TIPO_MOVIMENTO 
    ( 
     ID
    ) 
    NOT DEFERRABLE 
;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_MOVIMENTO REF TBL_USUARIO
prompt ==================================================
ALTER TABLE TBL_MOVIMENTO 
    ADD CONSTRAINT FK_MOVIMENTO_USUARIO FOREIGN KEY 
    ( 
     ID_USUARIO
    ) 
    REFERENCES TBL_USUARIO 
    ( 
     ID
    ) 
    ON DELETE CASCADE 
    NOT DEFERRABLE 
;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_CONTA_PAGAR_RECEBER REF TBL_CATEGORIA
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER 
    ADD CONSTRAINT FK_PAG_REC_CATEGORIA FOREIGN KEY 
    ( 
     ID_CATEGORIA
    ) 
    REFERENCES TBL_CATEGORIA 
    ( 
     ID
    ) 
    NOT DEFERRABLE 
;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_CONTA_PAGAR_RECEBER REF TBL_TIPO_MOVIMENTO
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER 
    ADD CONSTRAINT FK_PAG_REC_TIPO_MOV FOREIGN KEY 
    ( 
     ID_TIPO_MOVIMENTO
    ) 
    REFERENCES TBL_TIPO_MOVIMENTO 
    ( 
     ID
    ) 
    NOT DEFERRABLE 
;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_CONTA_PAGAR_RECEBER REF TBL_USUARIO
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER 
    ADD CONSTRAINT FK_PAG_REC_USUARIO FOREIGN KEY 
    ( 
     ID_USUARIO
    ) 
    REFERENCES TBL_USUARIO 
    ( 
     ID
    ) 
    ON DELETE CASCADE 
    NOT DEFERRABLE 
;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_SALDO REF TBL_USUARIO
prompt ==================================================
ALTER TABLE TBL_SALDO 
    ADD CONSTRAINT FK_SALDO_USUARIO FOREIGN KEY 
    ( 
     ID_USUARIO
    ) 
    REFERENCES TBL_USUARIO 
    ( 
     ID
    ) 
    ON DELETE CASCADE 
    NOT DEFERRABLE 
;

prompt ==================================================
prompt CREATE SEQUENCE ID_BANCO
prompt ==================================================
CREATE SEQUENCE TCC_PROJETO.SEQ_ID_BANCO 
    START WITH 1 
    INCREMENT BY 1 
    MAXVALUE 9999999999 
    MINVALUE 1 
;

prompt ==================================================
prompt CREATE SEQUENCE ID_CATEGORIA
prompt ==================================================
CREATE SEQUENCE TCC_PROJETO.SEQ_ID_CATEGORIA 
    START WITH 1 
    INCREMENT BY 1 
    MAXVALUE 9999999999 
    MINVALUE 1 
;

prompt ==================================================
prompt CREATE SEQUENCE ID_CLASSIFICA_AUTO
prompt ==================================================
CREATE SEQUENCE TCC_PROJETO.SEQ_ID_CLASSIFICA_AUTO 
    START WITH 1 
    INCREMENT BY 1 
    MAXVALUE 9999999999 
    MINVALUE 1 
;

prompt ==================================================
prompt CREATE SEQUENCE ID_CONTA
prompt ==================================================
CREATE SEQUENCE TCC_PROJETO.SEQ_ID_CONTA 
    START WITH 1 
    INCREMENT BY 1 
    MAXVALUE 999999999 
    MINVALUE 1 
;

prompt ==================================================
prompt CREATE SEQUENCE ID_CONTA_PAGAR_REC
prompt ==================================================
CREATE SEQUENCE TCC_PROJETO.SEQ_ID_CONTA_PAGAR_REC 
    START WITH 1 
    INCREMENT BY 1 
    MAXVALUE 999999999 
    MINVALUE 1 
;

prompt ==================================================
prompt CREATE SEQUENCE ID_MOVIMENTO
prompt ==================================================
CREATE SEQUENCE TCC_PROJETO.SEQ_ID_MOVIMENTO 
    START WITH 1 
    INCREMENT BY 1 
    MAXVALUE 9999999999 
    MINVALUE 1 
;

prompt ==================================================
prompt CREATE SEQUENCE ID_SALDO
prompt ==================================================
CREATE SEQUENCE TCC_PROJETO.SEQ_ID_SALDO 
    START WITH 1 
    INCREMENT BY 1 
    MAXVALUE 999999999 
    MINVALUE 1 
;

prompt ==================================================
prompt CREATE SEQUENCE ID_USUARIO
prompt ==================================================
CREATE SEQUENCE TCC_PROJETO.SEQ_ID_USUARIO 
    INCREMENT BY 1 
    MAXVALUE 999999999999 
    MINVALUE 1 
    CACHE 20 
;


prompt ==================================================
prompt ALTER TABLE TBL_SALDO COLUMN SALDO NUMBER(19,2)
prompt ==================================================
alter table TBL_SALDO modify SALDO NUMBER(19,2);

prompt ==================================================
prompt INSERT INTO TBL_TIPO_CONTA (Cartão, Conta Corrente, Dinheiro)
prompt ==================================================
insert into TBL_TIPO_CONTA (id, nome) values (1, 'Cartão de Crédito');
insert into TBL_TIPO_CONTA (id, nome) values (2, 'Conta Corrente');
insert into TBL_TIPO_CONTA (id, nome) values (3, 'Dinheiro');

prompt ==================================================
prompt INSERT INTO TIPO_MOVIMENTO (Débito, Crédito)
prompt ==================================================
insert into TBL_TIPO_MOVIMENTO (id,nome) values (1, 'Débito');
insert into TBL_TIPO_MOVIMENTO (id,nome) values (2, 'Crédito');

prompt ==================================================
prompt INSERT INTO TBL_USUARIO (ADM)
prompt ==================================================
INSERT INTO TBL_USUARIO (ID, NOME, PASSWORD, USERNAME, EMAIL, PRIMEIRA_VEZ) 
	VALUES 
		(seq_id_usuario.nextval,'Administrador', 'adm', 'adm', 'administrador@sisconfip.com', 'N');

commit;

prompt ==================================================
prompt INSERT INTO TBL_BANCO
prompt ==================================================
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco do Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco da Amazônia S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco do Nordeste do Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Santander Meridional S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'BANESTES S.A. Banco do Estado do Espírito Santo');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco de Pernambuco S.A. - BANDEPE');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Alfa S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco do Estado de Santa Catarina S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Banerj S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Beg S.A.');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Santander Banespa S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco BEC S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Bem S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco do Estado do Pará S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Banestado S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco do Estado do Piauí S.A. - BEP');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Cargill S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco do Estado do Rio Grande do Sul S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco BVA S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Opportunity S.A.');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco do Estado de Sergipe S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Itaú BBA S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Hipercard Banco Múltiplo S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Ibi S.A. Banco Múltiplo');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Goldman Sachs do Brasil Banco Múltiplo S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Lemon Bank Banco Múltiplo S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Morgan Stanley Dean Witter S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'BPN Brasil Banco Múltiplo S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'BRB - Banco de Brasília S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Rural Mais S.A.');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'BB Banco Popular do Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco J. Safra S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco CR2 S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco KDB S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco BMeF Serviços Liquidação e Custódia S.A');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Caixa Econômica Federal');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco BBM S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Único S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Nossa Caixa S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Finasa S.A.');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'American Express Bank Brasil Banco Múltiplo S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Pactual S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Dresdner Bank Lateinamerika Aktiengesellschaft');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Matone S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Arbi S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Dibens S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Comercial e de Investimento Sudameris S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco John Deere S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Bonsucesso S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Calyon Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Fibra S.A.');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Brascan S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Cruzeiro do Sul S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Unicard Banco Múltiplo S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco GE Capital S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Bradesco S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Clássico S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Máxima S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco ABC Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco UBS S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Boavista Interatlântico S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Investcred Unibanco S.A.');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Schahin S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Fininvest S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Paraná Banco S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Cacique S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Fator S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Cédula S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco de La Nacion Argentina');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco BMG S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Industrial e Comercial S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Itaú S.A.');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Sudameris Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Santander S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Santander Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco ABN AMRO Real S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Société Générale Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco WestLB do Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco WestLB do Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Mercantil do Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Mercantil de São Paulo S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco BMC S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'HSBC Bank Brasil S.A. - Banco Múltiplo');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'UNIBANCO - União de Bancos Brasileiros S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Capital S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Safra S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Rural S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco de Tokyo-Mitsubishi UFJ Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Sumitomo Mitsui Brasileiro S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Citibank N.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'BankBoston Banco Múltiplo S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Deutsche Bank S.A. - Banco Alemão');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'JPMorgan Chase Bank');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'ING Bank N.V.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco de La Republica Oriental del Uruguay');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco de La Provincia de Buenos Aires');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Credit Suisse (Brasil) S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Luso Brasileiro S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Industrial do Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco VR S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Paulista S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Guanabara S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Pecúnia S.A.');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Panamericano S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Ficsa S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Intercap S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Rendimento S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Triângulo S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Sofisa S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Prosper S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Alvorada S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Pine S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Itaú Holding Financeira S.A.');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Indusval S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco A.J.Renner S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Votorantim S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Daycoval S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banif-Banco Internacional do Funchal (Brasil)S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Credibel S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Porto Seguro S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Gerdau S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Pottencial S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Morada S.A.');
commit;

insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco BGN S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Barclays S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Ribeirão Preto S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Semear S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'BankBoston N.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Citibank S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Modal S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Rabobank International Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Cooperativo Sicredi S.A. - BANSICREDI');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Simples S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Dresdner Bank Brasil S.A. - Banco Múltiplo');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco BNP Paribas Brasil S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Comercial Uruguai S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Merrill Lynch de Investimentos S.A.');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco Cooperativo do Brasil S.A. - BANCOOB');
insert into tbl_banco (ID, NOME) values (SEQ_ID_BANCO.NEXTVAL, 'Banco KEB do Brasil S.A.');

commit;

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Benefícios)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (1, 'Benefícios', 2);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Aposentadoria', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Pensão Alimentícia', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Plano de Providência', 1);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Outros Rendimentos)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (2, 'Outros Rendimentos', 2);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Loterias', 2);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Pensão', 2);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Principal do Empréstimo', 2);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Seguro Desemprego', 2);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Proventos)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (3, 'Proventos', 2);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Bônus', 3);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Comissão', 3);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Horas Extras', 3);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Salários', 3);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Rendimento de aposentadoria)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (4, 'Rendimento de Aposentadoria', 2);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Benefícios do INSS', 4);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Previdência Privada', 4);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Rendimento de investimentos)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (5, 'Rendimento de Investimentos', 2);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Ganhos de Capital', 5);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Juros', 5);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Juros não-tributáveis', 5);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Lucros de Dividendos', 5);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Outros', 5);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Renda de Aluguel', 5);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Transferência entre contas)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (6, 'Transferência entre contas', 2);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Transferência', 6);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Transferência Fundos', 6);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Transferência Investimentos', 6);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Transferência Poupança', 6);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Rendimento de alimentação)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (7, 'Alimentação', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Feira', 7);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Outros', 7);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Padaria', 7);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Restaurantes', 7);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Supermercado', 7);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Animal de estimação)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (8, 'Animal de Estimação', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Animal de Estimação', 8);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Suprimentos', 8);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Veterinário', 8);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Bancos)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (9, 'Bancos', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Juros', 9);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Despesa com filhos)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (10, 'Despesas com Filhos', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Alimentação', 10);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Babá', 10);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Escola', 10);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Lazer', 10);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Médico', 10);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Mesada', 10);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Vestuário', 10);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Despesa de trabalho)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (11, 'Despesas de Trabalho', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Despesas de Trabalho', 11);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Educação)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (12, 'Educação', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Cursos e Treinamentos', 12);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Escola/Universidade', 12);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Livros', 12);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Outros Gastos', 12);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Empregados)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (13, 'Empregados', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Babá', 13);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Doméstica', 13);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Faxineira/Passadeira', 13);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Jardineiro', 13);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Motorista', 13);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Empréstimo)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (14, 'Empréstimo', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Empréstimo', 14);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Juros do Empréstimo', 14);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Estética)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (15, 'Estética', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Cabelereiro/Manicure/Pedicure', 15);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Musculação', 15);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Impostos)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (16, 'Impostos', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'CPMF', 16);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'FGTS', 16);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Impostos Locais', 16);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'INSS', 16);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'IOF', 16);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'IPTU', 16);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'IPVA', 16);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'IRRF', 16);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Outros Impostos', 16);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'PIS', 16);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Investimentos)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (17, 'Investimentos', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Imóvel', 17);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Itens domésticos)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (18, 'Itens Domésticos', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Mobiliário', 18);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Utensílios', 18);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Lazer)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (19, 'Lazer', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Brinquedos e Jogos', 19);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Cinema e Locação', 19);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Diversão', 19);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Eventos Culturais', 19);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Eventos Esportivos', 19);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Férias', 19);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Fitas e CDs', 19);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Livros e Revistas', 19);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Viagem', 19);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Moradia)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (20, 'Moradia', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Água e Esgoto', 20);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Aluguel', 20);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Condicionadores de Ar', 20);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Condomínio', 20);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Gás', 20);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Juros Financ. Imobiliário', 20);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Lixo e Reciclagem', 20);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Luz', 20);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Outros)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (21, 'Outros', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Outros ', 21);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Presentes', 21);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Saúde)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (22, 'Saúde', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Dentista', 22);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Fisioterapia', 22);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Hospital', 22);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Médico', 22);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Ótica', 22);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Outros', 22);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Remédios', 22);

prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Seguros)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (23, 'Seguros', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Automóvel', 23);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Do Locador/Do Locatário', 23);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Outros', 23);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Saúde', 23);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Vida', 23);
prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Taxas Bancarias)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (24, 'Taxas Bancárias', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Taxa de Serviço ', 24);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Taxas Bancárias', 24);
prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Telecomunicações)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (25, 'Telecomunicações', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Celular', 25);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Internet', 25);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Telefone Fixo', 25);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'TV a cabo', 25);
prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Trannsferências entre contas)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (26, 'Transferência entre Contas', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Transferência', 26);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Transferência Investimentos', 26);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Tranferência Poupança', 26);
prompt ==================================================
prompt INSERT INTO TBL_GRUPO_CATEGORIA (Transportes)
prompt ==================================================
insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (27, 'Transportes', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Avião', 27);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Catamarã', 27);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Combustível', 27);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Estacionamento', 27);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Manutenção', 27);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Metrô', 27);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Ônibus', 27);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Prestação do Carro', 27);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Taxi', 27);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Trem', 27);

insert into TBL_GRUPO_CATEGORIA (ID, GRUPO, ID_TIPO_MOVIMENTO) values (28, 'Vestuário', 1);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Lavanderia/Tinturaria', 28);
insert into TBL_CATEGORIA (ID, NOME, ID_GRUPO_CATEGORIA) values (SEQ_ID_CATEGORIA.nextval, 'Roupas e Acessórios', 28);

commit;

prompt ==================================================
prompt ALTER TABLE TBL_CONTA MODIFY COLUMN SALDO_INICIAL NUMBER(19,2)
prompt ==================================================
ALTER TABLE TBL_CONTA MODIFY SALDO_INICIAL NUMBER(19,2);

prompt ==================================================
prompt CREATE TABLE TBL_LOG_SALDO
prompt ==================================================
CREATE TABLE TBL_LOG_SALDO (
       ID NUMBER NOT NULL,
       ID_USUARIO NUMBER NOT NULL,
       SALDO_INICIAL NUMBER(19,2) NOT NULL,
	   DATA_ATUALIZACAO DATE NOT NULL
);

prompt ==================================================
prompt CREATE PRIMARY KEY TBL_LOG_SALDO
prompt ==================================================
ALTER TABLE TBL_LOG_SALDO ADD CONSTRAINT PK_LOG_SALDO PRIMARY KEY (ID);

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_LOG_SALDO REF TBL_USUARIO
prompt ==================================================
ALTER TABLE TBL_LOG_SALDO ADD CONSTRAINT FK_LOG_CONTA_USUARIO FOREIGN KEY (ID_USUARIO) REFERENCES TBL_USUARIO (ID);

prompt ==================================================
prompt CREATE SEQUENCE ID_LOG_SALDO
prompt ==================================================
CREATE SEQUENCE SEQ_ID_LOG_SALDO
MINVALUE 0
MAXVALUE 9999999999
START WITH 1
INCREMENT BY 1;

prompt ==================================================
prompt DROP TABLE TBL_SALDO
prompt ==================================================
DROP TABLE TBL_SALDO;

prompt ==================================================
prompt ALTER TABLE TBL_CONTA_PAGAR_RECEBER MODIFY VALOR NUMBER(19,2)
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER MODIFY VALOR NUMBER(19,2);

prompt ==================================================
prompt ALTER TABLE TBL_LOG_CONTA DROP FOREIGN KEY FK_LOG_CONTA_CONTA
prompt ==================================================
ALTER TABLE TBL_LOG_CONTA DROP CONSTRAINT FK_LOG_CONTA_CONTA;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_MOVIMENTO REF TBL_CONTA
prompt ==================================================
ALTER TABLE TBL_MOVIMENTO ADD CONSTRAINT FK_MOVIMENTO_ID_CONTA 
FOREIGN KEY (ID_CONTA) REFERENCES TBL_CONTA (ID) ON DELETE SET NULL;

prompt ==================================================
prompt ALTER TABLE TBL_CONTA_PAGAR_RECEBER DROP COLUMN ID_USUARIO
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER DROP COLUMN ID_USUARIO; 

prompt ==================================================
prompt ALTER TABLE TBL_CONTA_PAGAR_RECEBER ADD COLUMN ID_CONTA
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER ADD ID_CONTA NUMBER NULL;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_CONTA_PAGAR_RECEBER REF TBL_CONTA
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER ADD CONSTRAINT FK_CONTA_PAG_ID_CONTA 
FOREIGN KEY (ID_CONTA) REFERENCES TBL_CONTA (ID);

prompt ==================================================
prompt ALTER TABLE TBL_CONTA_PAGAR_RECEBER ADD COLUMN
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER ADD ID_USUARIO NUMBER NOT NULL;

prompt ==================================================
prompt CREATE FOREIGN KEY TBL_CONTA_PAGAR_RECEBER REF TBL_USUARIO
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER ADD CONSTRAINT FK_CONTA_PAG_USUARIO 
FOREIGN KEY (ID_USUARIO) REFERENCES TBL_USUARIO (ID) ON DELETE CASCADE;

prompt ==================================================
prompt ALTER TABLE TBL_CONTA_PAGAR_RECEBER ADD COLUMN
prompt ==================================================
ALTER TABLE TBL_CONTA_PAGAR_RECEBER ADD DATA_PAGAMENTO DATE NULL;

prompt ==================================================
prompt CREATE INDEX IDX_ID_CATEGORIA ON COLUMN ID_CATEGORIA
prompt ==================================================
create index IDX_ID_CATEGORIA on TBL_CONTA_PAGAR_RECEBER (ID_CATEGORIA)

prompt ==================================================
prompt ALTER TABLE TBL_CONTA_PAGAR_RECEBER DROP CONSTRAINT
prompt ==================================================
alter table TBL_CONTA_PAGAR_RECEBER
  drop constraint FK_CONTA_PAG_ID_CONTA;

prompt ==================================================
prompt ALTER TABLE TBL_CONTA_PAGAR_RECEBER ADD CONSTRAINT
prompt ==================================================
alter table TBL_CONTA_PAGAR_RECEBER
  add constraint FK_CONTA_PAG_ID_CONTA foreign key (ID_CONTA)
  references TBL_CONTA (ID) on delete set null;

prompt ==================================================
prompt CREATE TRIGGER trg_calcula_contas_receber
prompt ==================================================
create or replace trigger trg_calcula_contas_receber
  after update or delete on tbl_conta_pagar_receber
  for each row

declare

begin
  if :new.pago = 'S' or :old.pago = 'S' then
    if updating then
      /*Se for exclusão de conta não atualiza o registro.*/
      if :new.id_conta is not null then
        /*Se for crédito soma no saldo*/
        if :new.id_tipo_movimento = 2 then
          update tbl_conta tc
             set tc.saldo_inicial = tc.saldo_inicial + :new.valor
           where tc.id = :new.id_conta;
          /*Se for débito retira do saldo*/
        elsif :new.id_tipo_movimento = 1 then
          update tbl_conta tc
             set tc.saldo_inicial = tc.saldo_inicial - :new.valor
           where tc.id = :new.id_conta;
        end if;
      end if;
    elsif deleting then
      /*Se for crédito retorna o valor*/
      if :old.id_tipo_movimento = 2 then
        update tbl_conta tc
           set tc.saldo_inicial = tc.saldo_inicial - :old.valor
         where tc.id = :old.id_conta;
        /*Se for débito soma de volta o valor*/
      elsif :old.id_tipo_movimento = 1 then
        update tbl_conta tc
           set tc.saldo_inicial = tc.saldo_inicial + :old.valor
         where tc.id = :old.id_conta;
      end if;
    end if;
  end if;
end trg_calcula_contas_receber;
/  

prompt ==================================================
prompt ALTER TABLE TBL_USUARIO ADD COLUMN
prompt ==================================================
alter table TBL_USUARIO add DATA_CRIACAO DATE NULL;

prompt ==================================================
prompt ALTER TABLE TBL_CONTA_PAGAR_RECEBER ADD UNIQUE
prompt ==================================================
alter table TBL_USUARIO
  add constraint AK_EMAIL unique (EMAIL);

prompt ==================================================
prompt CREATE TABLE TBL_LOG_CONTAS
prompt ==================================================
create table tbl_log_contas as
        select *
          from tbl_conta;  

prompt ==================================================
prompt CREATE TRIGGER TRG_LOG_SALDO
prompt ==================================================
create or replace trigger trg_log_saldo
  after insert or update or delete on tbl_conta
  for each row
declare
  v_n_saldo      number(19, 2) := 0;
  v_n_id_usuario number;
begin

  /* Bloco para criação de uma réplica da tabela de contas */

  if inserting then
  
    v_n_id_usuario := :new.id_usuario;
  
    begin
      insert into tbl_log_contas
        (id,
         nome,
         saldo_inicial,
         id_usuario,
         id_banco,
         data_validade,
         limite_cartao,
         id_tipo_conta)
      values
        (:new.id,
         :new.nome,
         :new.saldo_inicial,
         :new.id_usuario,
         :new.id_banco,
         :new.data_validade,
         :new.limite_cartao,
         :new.id_tipo_conta);
    exception
      when others then
        null;
    end;
  elsif deleting then
  
    v_n_id_usuario := :old.id_usuario;
  
    begin
      delete from tbl_log_contas where id = :old.id;
    exception
      when others then
        null;
    end;
  
  elsif updating then
  
    v_n_id_usuario := :new.id_usuario;
  
    begin
      update tbl_log_contas
         set nome          = :new.nome,
             saldo_inicial = :new.saldo_inicial,
             id_usuario    = :new.id_usuario,
             id_banco      = :new.id_banco,
             data_validade = :new.data_validade,
             limite_cartao = :new.limite_cartao,
             id_tipo_conta = :new.id_tipo_conta
       where id = :new.id;
    exception
      when others then
        null;
    end;
  
  end if;

  /*
   Pega o saldo total de todas as contas da tabela de log de conta.
  */

  select nvl(sum(l.saldo_inicial),0)
    into v_n_saldo
    from tbl_log_contas l
   where l.id_usuario = v_n_id_usuario;

  /*
  Insere a soma do saldo na tabela de log de saldo.
  */
  insert into tbl_log_saldo
    (id, id_usuario, saldo_inicial, data_atualizacao)
  values
    (seq_id_log_saldo.nextval, v_n_id_usuario, v_n_saldo, sysdate);

end trg_log_saldo;
/

create or replace trigger trg_log_saldo_movimento
  after insert or delete on tbl_movimento
  for each row
declare
  v_n_saldo_conta number(19, 2) := 0;
  v_n_id_usuario  number;
  v_n_saldo_total number(19, 2) := 0;
begin

  /* Bloco para criação de uma réplica da tabela de contas */

  if inserting then
  
    v_n_id_usuario := :new.id_usuario;
  
    begin
      select tc.saldo_inicial
        into v_n_saldo_conta
        from tbl_conta tc
       where tc.id_usuario = v_n_id_usuario
         and tc.id = :new.id_conta;
      if (:new.id_tipo_movimento = 1) then
        v_n_saldo_total := v_n_saldo_conta - :new.valor;
      else
        v_n_saldo_total := v_n_saldo_conta + :new.valor;
      end if;
    
      update tbl_conta
         set saldo_inicial = v_n_saldo_total
       where id_usuario = v_n_id_usuario
         and id = :new.id_conta;
    
    exception
      when others then
        null;
    end;
    
  elsif deleting then
  
    v_n_id_usuario := :old.id_usuario;
  
    begin
      select tc.saldo_inicial
        into v_n_saldo_conta
        from tbl_conta tc
       where tc.id_usuario = v_n_id_usuario
         and tc.id = :old.id_conta;
         
      if (:old.id_tipo_movimento = 1) then
        v_n_saldo_total := v_n_saldo_conta + :old.valor;
      else
        v_n_saldo_total := v_n_saldo_conta - :old.valor;
      end if;
    
      update tbl_conta
         set saldo_inicial = v_n_saldo_total
       where id_usuario = v_n_id_usuario
         and id = :old.id_conta;
    
    exception
      when others then
        null;
    end;
  
  end if;
end trg_log_saldo;
/

spool off