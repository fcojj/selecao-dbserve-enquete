CREATE TABLE  IF NOT EXISTS `tipo_usuario`(
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username_id int(11) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username_id),
  KEY fk_username_idx (username_id),
  CONSTRAINT fk_username FOREIGN KEY (username_id) REFERENCES usuario (id));