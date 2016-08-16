-- -----------------------------------------------------
-- Table `enquete`.`estabelecimento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enquete`.`estabelecimento` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `telefone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `enquete`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enquete`.`usuario` (
  `id` INT(11) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `enabled` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `enquete`.`votacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enquete`.`votacao` (
  `data` DATE NOT NULL,
  `estabelecimento_id` INT(11) NOT NULL,
  `usuario_id` INT(11) NOT NULL,
  PRIMARY KEY (`data`, `usuario_id`),
  INDEX `fk_votacao_estabelecimento_idx` (`estabelecimento_id` ASC),
  INDEX `fk_votacao_usuario1_idx` (`usuario_id` ASC),
  CONSTRAINT `fk_votacao_estabelecimento`
    FOREIGN KEY (`estabelecimento_id`)
    REFERENCES `enquete`.`estabelecimento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_votacao_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `enquete`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
