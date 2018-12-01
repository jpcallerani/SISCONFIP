-- executa a criação da tablespace de dados 
CREATE TABLESPACE TCC_PROJETO_DATA 
  DATAFILE 'C:\oraclexe\app\oracle\oradata\XE\TCC_PROJETO_DATA01.ORA' 
  SIZE 20M AUTOEXTEND ON NEXT  20M 
  MAXSIZE  4000M FORCE LOGGING; 
  
  -- executa a criação da tablespace de indexes 
CREATE TABLESPACE TCC_PROJETO_INDEX 
  DATAFILE 'C:\oraclexe\app\oracle\oradata\XE\TCC_PROJETO_INDEX01.ORA' 
  SIZE 20M AUTOEXTEND ON NEXT  20M 
  MAXSIZE 4000M FORCE LOGGING;
  
  -- executa a criação do usuário
CREATE USER TCC_PROJETO   
  IDENTIFIED BY TCC_PROJETO
  DEFAULT TABLESPACE TCC_PROJETO_DATA 
  QUOTA UNLIMITED ON TCC_PROJETO_DATA 
  QUOTA UNLIMITED ON TCC_PROJETO_INDEX; 
grant alter session, 
        create session, 
        create database link, 
        create procedure, 
        create sequence, 
        create synonym, 
        create table, 
        create trigger, 
        create type, 
        create view, 
        debug any procedure, 
        debug connect session to TCC_PROJETO; 