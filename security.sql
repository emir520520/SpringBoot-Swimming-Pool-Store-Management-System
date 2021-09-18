/*create table*/
create table SEC_USER
(
  userID           BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userName         VARCHAR(36) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  ENABLED           BIT NOT NULL 
) ;

create table SEC_ROLE
(
  roleID   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
) ;


create table USER_ROLE
(
  ID      BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userID BIGINT NOT NULL,
  roleID BIGINT NOT NULL
);

/*Set foreign key*/
alter table USER_ROLE
  add constraint USER_ROLE_UK unique (userId, roleID);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (userID)
  references SEC_USER (userID);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (roleID)
  references SEC_ROLE (roleID);
  
  
 /*Insert Data*/
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Jon', '$2b$10$Y7R5biEi/wVfa8LKX3N4XetL7E/HaJXr7a9VGV039qGvSG9r2geIy', 1);
 
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Tod', '$2b$10$Y7R5biEi/wVfa8LKX3N4XetL7E/HaJXr7a9VGV039qGvSG9r2geIy', 1);


insert into sec_role (roleName)
values ('ROLE_BOSS');

insert into sec_role (roleName)
values ('ROLE_WORKER');


insert into user_role (userID, roleID)
values (1, 1);
 
insert into user_role (userID, roleID)
values (2, 2);


COMMIT;

