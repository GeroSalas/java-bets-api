
-- -----------------------------------------------------
-- Setup DesafioApp DATABASE
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bet_user_points` ;
DROP TABLE IF EXISTS `bets` ;
DROP TABLE IF EXISTS `matches` ;
DROP TABLE IF EXISTS `teams` ;
DROP TABLE IF EXISTS `likes` ;
DROP TABLE IF EXISTS `posts_has_sectors` ;
DROP TABLE IF EXISTS `posts` ;
DROP TABLE IF EXISTS `tickets` ;
DROP TABLE IF EXISTS `user_roles` ;
DROP TABLE IF EXISTS `d_users` ;
DROP TABLE IF EXISTS `rewards` ;
DROP TABLE IF EXISTS `user_settings` ;
DROP TABLE IF EXISTS `sectors` ;
DROP TABLE IF EXISTS `clients` ;

-- -----------------------------------------------------
-- Table `clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clients` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `logo_image` VARCHAR(200) NULL DEFAULT NULL,
  `background_image` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 103
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rewards`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rewards` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(80) NOT NULL,
  `winner_position` INT(11) NOT NULL,
  `custom_award_name` VARCHAR(80) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sectors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sectors` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `user_settings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_settings` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `style_1_color` VARCHAR(40) NOT NULL,
  `style_2_color` VARCHAR(40) NOT NULL,
  `style_3_color` VARCHAR(40) NOT NULL,
  `style_4_color` VARCHAR(40) NOT NULL,
  `style_5_color` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `d_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `d_users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(50) NOT NULL,
  `lastname` VARCHAR(50) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `enabled` TINYINT(4) NOT NULL DEFAULT '1',
  `pb_mobile_token` VARCHAR(220) NULL DEFAULT NULL,
  `pb_platform` INT(11) NULL DEFAULT NULL,
  `on_play` TINYINT(1) NOT NULL DEFAULT '0',
  `gender` VARCHAR(1) NULL DEFAULT NULL,
  `age` INT(11) NULL DEFAULT NULL,
  `ranking_points` INT(11) NULL DEFAULT NULL,
  `profile_image` VARCHAR(200) NULL DEFAULT NULL,
  `client_id` BIGINT(20) NULL DEFAULT NULL,
  `sector_id` BIGINT(20) NULL DEFAULT NULL,
  `reward_id` BIGINT(20) NULL DEFAULT NULL,
  `user_preferences_id` BIGINT(20) NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `fk_users_clients1_idx` (`client_id` ASC),
  INDEX `fk_users_sectors1_idx` (`sector_id` ASC),
  INDEX `fk_users_rewards1_idx` (`reward_id` ASC),
  INDEX `fk_users_user_settings1_idx` (`user_preferences_id` ASC),
  CONSTRAINT `fk_users_clients1`
    FOREIGN KEY (`client_id`)
    REFERENCES `clients` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_rewards1`
    FOREIGN KEY (`reward_id`)
    REFERENCES `rewards` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_sectors1`
    FOREIGN KEY (`sector_id`)
    REFERENCES `sectors` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_user_settings1`
    FOREIGN KEY (`user_preferences_id`)
    REFERENCES `user_settings` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teams` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `flag_img` VARCHAR(200) NOT NULL,
  `shield_img` VARCHAR(200) NOT NULL,
  `team_group` INT(11) NOT NULL,
  `points_group` INT(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `matches`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matches` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `local_team_id` BIGINT(20) NULL DEFAULT NULL,
  `visitor_team_id` BIGINT(20) NULL DEFAULT NULL,
  `start_date` DATETIME NOT NULL,
  `playoff` TINYINT(1) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `local_score` INT(11) NULL DEFAULT NULL,
  `visitor_score` INT(11) NULL DEFAULT NULL,
  `has_extra_time` TINYINT(1) NOT NULL DEFAULT '0',
  `local_score_et` INT(11) NULL DEFAULT NULL,
  `visitor_score_et` INT(11) NULL DEFAULT NULL,
  `has_penalties` TINYINT(1) NOT NULL DEFAULT '0',
  `local_score_pen` INT(11) NULL DEFAULT NULL,
  `visitor_score_pen` INT(11) NULL DEFAULT NULL,
  `result_text` VARCHAR(40) NOT NULL DEFAULT 'X-X',
  `result_extra_text` VARCHAR(40) NULL DEFAULT NULL,
  `winner` VARCHAR(20) NULL DEFAULT NULL,
  `is_closed` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_matches_teams1_idx` (`local_team_id` ASC),
  INDEX `fk_matches_teams2_idx` (`visitor_team_id` ASC),
  CONSTRAINT `fk_matches_teams1`
    FOREIGN KEY (`local_team_id`)
    REFERENCES `teams` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_matches_teams2`
    FOREIGN KEY (`visitor_team_id`)
    REFERENCES `teams` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bets` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `bet_type` INT(11) NOT NULL,
  `exact_score_result` VARCHAR(10) NULL DEFAULT NULL,
  `exact_team_result` VARCHAR(20) NULL DEFAULT NULL,
  `exact_group_winner_result` VARCHAR(50) NULL DEFAULT NULL,
  `related_match_id` BIGINT(20) NULL DEFAULT NULL,
  `related_group_name` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_bets_matches1_idx` (`related_match_id` ASC),
  CONSTRAINT `fk_bets_matches1`
    FOREIGN KEY (`related_match_id`)
    REFERENCES `matches` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 37
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bet_user_points`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bet_user_points` (
  `bet_id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  `bet_team_result` VARCHAR(20) DEFAULT NULL,
  `bet_score_result` VARCHAR(20) DEFAULT NULL,
  `bet_group_result` VARCHAR(20) DEFAULT NULL,
  `points` INT(11) NULL DEFAULT NULL,
  `notified_on_timeline` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`bet_id`, `user_id`),
  INDEX `fk_bets_has_users_bets1_idx` (`bet_id` ASC),
  INDEX `fk_bet_user_points_users2_idx` (`user_id` ASC),
  CONSTRAINT `fk_bet_user_points_users2`
    FOREIGN KEY (`user_id`)
    REFERENCES `d_users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bets_has_users_bets1`
    FOREIGN KEY (`bet_id`)
    REFERENCES `bets` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `posts` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `posted_text` VARCHAR(250) NOT NULL,
  `posted_date` DATETIME NOT NULL,
  `posted_image_1` VARCHAR(200) NULL DEFAULT NULL,
  `posted_image_2` VARCHAR(200) NULL DEFAULT NULL,
  `posted_image_3` VARCHAR(200) NULL DEFAULT NULL,
  `posted_image_4` VARCHAR(200) NULL DEFAULT NULL,
  `posted_image_5` VARCHAR(200) NULL DEFAULT NULL,
  `posted_image_6` VARCHAR(200) NULL DEFAULT NULL,
  `posted_image_7` VARCHAR(200) NULL DEFAULT NULL,
  `posted_image_8` VARCHAR(200) NULL DEFAULT NULL,
  `user_id` BIGINT(20) NOT NULL,
  `is_comment` TINYINT(1) NOT NULL,
  `notified_on_timeline` TINYINT(1) NOT NULL DEFAULT '0',
  `parent_post_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_posts_d_users1_idx` (`user_id` ASC),
  INDEX `fk_posts_posts1_idx` (`parent_post_id` ASC),
  CONSTRAINT `fk_posts_d_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `d_users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_posts_posts1`
    FOREIGN KEY (`parent_post_id`)
    REFERENCES `posts` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `likes` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `notified_on_timeline` TINYINT(1) NOT NULL DEFAULT '0',
  `related_post_id` BIGINT(20) NOT NULL,
  `related_user_liked_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_likes_posts1_idx` (`related_post_id` ASC),
  INDEX `fk_likes_d_users1_idx` (`related_user_liked_id` ASC),
  CONSTRAINT `fk_likes_posts1`
    FOREIGN KEY (`related_post_id`)
    REFERENCES `posts` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_likes_d_users1`
    FOREIGN KEY (`related_user_liked_id`)
    REFERENCES `d_users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tickets` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ticket_type` INT(11) NOT NULL,
  `title` VARCHAR(60) NULL DEFAULT NULL,
  `description` VARCHAR(200) NOT NULL,
  `posted_date` DATETIME NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  `is_comment` TINYINT(1) NOT NULL,
  `parent_ticket_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tickets_users1_idx` (`user_id` ASC),
  INDEX `fk_tickets_tickets1_idx` (`parent_ticket_id` ASC),
  CONSTRAINT `fk_tickets_tickets1`
    FOREIGN KEY (`parent_ticket_id`)
    REFERENCES `tickets` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tickets_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `d_users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_roles` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `rolename` VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
  `user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_roles_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_roles_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `d_users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `posts_has_sectors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `posts_has_sectors` (
  `post_id` BIGINT(20) NOT NULL,
  `sector_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`post_id`, `sector_id`),
  INDEX `fk_posts_has_sectors_sectors1_idx` (`sector_id` ASC),
  INDEX `fk_posts_has_sectors_posts1_idx` (`post_id` ASC),
  CONSTRAINT `fk_posts_has_sectors_posts1`
    FOREIGN KEY (`post_id`)
    REFERENCES `posts` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_posts_has_sectors_sectors1`
    FOREIGN KEY (`sector_id`)
    REFERENCES `sectors` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Procedure SP_Update_Match_Scores
-- -----------------------------------------------------
DROP procedure IF EXISTS `SP_Update_Match_Scores`;

DELIMITER $$

CREATE PROCEDURE `SP_Update_Match_Scores`(

	IN match_id_in BIGINT,
	IN local_team_id_in BIGINT,
	IN visitor_team_id_in BIGINT,
	IN local_score_in INT,
	IN visitor_score_in INT,
	IN is_extratime_in TINYINT(1),
	IN local_score_et_in INT,
	IN visitor_score_et_in INT,
	IN is_penalties_in TINYINT(1),
	IN local_score_pen_in INT,
	IN visitor_score_pen_in INT,
	IN result_text_in VARCHAR(40),
	IN result_extra_text_in VARCHAR(40),
	IN winner_in VARCHAR(20))
	
BEGIN

	 DECLARE newlocalpts INT;
	 DECLARE newvisitorpts INT;
	 DECLARE isplayoff TINYINT(1);
	 DECLARE finished INT;


	/* ACTUALIZACION DE RESULTADO PARTIDO TERMINADO */

	UPDATE matches AS m 
	SET 
	 m.result_text = result_text_in,   /*  Este Resultado a los 90MIN vale para las apuestas */
	 m.result_extra_text = IF(result_extra_text_in="-", NULL, result_extra_text_in),
	 m.local_score = local_score_in,
	 m.visitor_score = visitor_score_in,
	 m.has_extra_time = is_extratime_in,
	 m.local_score_et = IF(is_extratime_in, local_score_et_in, NULL),        /* IF(local_score_et_in IS NOT NULL, local_score_et_in, NULL) */        /* Hack for Issue HHH-9007 */  
	 m.visitor_score_et = IF(is_extratime_in, local_score_et_in, NULL),      /* IF(visitor_score_et_in IS NOT NULL, visitor_score_et_in, NULL) */
	 m.has_penalties = is_penalties_in,
	 m.local_score_pen = IF(is_penalties_in, local_score_pen_in, NULL),      /* IF(local_score_pen_in IS NOT NULL, local_score_pen_in, NULL) */
	 m.visitor_score_pen = IF(is_penalties_in, visitor_score_pen_in, NULL),  /* IF(visitor_score_pen_in IS NOT NULL, visitor_score_pen_in, NULL) */
	 m.winner = winner_in,
	 m.is_closed = true
	WHERE m.id = match_id_in;

	
	SET isplayoff = (SELECT playoff FROM matches WHERE id=match_id_in);

	IF (NOT(isplayoff)) THEN   /*  Si es partido en Fase de Grupos sumamos los puntos solamente */

	
	 IF local_score_in = visitor_score_in

	  THEN SET newlocalpts = 1, newvisitorpts = 1;

	 ELSEIF local_score_in > visitor_score_in 

	  THEN SET newlocalpts = 3, newvisitorpts = 0;

	 ELSE SET newlocalpts = 0, newvisitorpts = 3;

	 END IF;


	 UPDATE teams SET points_group = points_group + newlocalpts WHERE id = local_team_id_in;
	 UPDATE teams SET points_group = points_group + newvisitorpts WHERE id = visitor_team_id_in;
	

	END IF;


	
	/* ACTUALIZACION DE RESULTADOS DE APUESTAS FIJAS */

	/* Apuesta Type 1 de Equipo Ganador y/o Resultado Exacto   */
	UPDATE bets SET exact_team_result = winner_in, exact_score_result = result_text_in WHERE bet_type=1 AND related_match_id = match_id_in; 


	/* Apuesta Type 2 de Ganador de Grupo */
	IF (match_id_in = 24) THEN 

	 UPDATE bets SET exact_group_winner_result = (SELECT name FROM teams WHERE team_group=1 ORDER BY points_group DESC LIMIT 1) WHERE bet_type=2 AND related_group_name=1;  /*  Grupo A */
	 UPDATE bets SET exact_group_winner_result = (SELECT name FROM teams WHERE team_group=2 ORDER BY points_group DESC LIMIT 1) WHERE bet_type=2 AND related_group_name=2;  /*  Grupo B */
	 UPDATE bets SET exact_group_winner_result = (SELECT name FROM teams WHERE team_group=3 ORDER BY points_group DESC LIMIT 1) WHERE bet_type=2 AND related_group_name=3;  /*  Grupo C */
	 UPDATE bets SET exact_group_winner_result = (SELECT name FROM teams WHERE team_group=4 ORDER BY points_group DESC LIMIT 1) WHERE bet_type=2 AND related_group_name=4;  /*  Grupo D */

	END IF;

	


	/* ASIGNACION DE PUNTOS CORRESPONDIENTES A CADA APUESTA DE USUARIO PARTICIPANTE EN JUEGO */

	/* Apuesta de Usuario - Type 1 de Equipo Ganador   */

	UPDATE bet_user_points AS bu INNER JOIN bets AS b ON bu.bet_id=b.id , d_users AS u
      SET 
	     bu.points = IF(bu.bet_team_result = b.exact_team_result, 7, 0),
		 u.ranking_points = u.ranking_points + IF(bu.bet_team_result = b.exact_team_result, 7, 0)
	   WHERE b.bet_type = 1 AND u.id=bu.user_id;

	
	/* Agregar puntos extras por Resultado Exacto de goles */

	UPDATE bet_user_points AS bu INNER JOIN bets AS b ON bu.bet_id=b.id , d_users AS u
      SET 
	     bu.points = bu.points + IF(bu.bet_score_result = b.exact_score_result, 15,
	                      IF((SUBSTRING(bu.bet_score_result, 1, 1) = SUBSTRING(b.exact_score_result, 1, 1)) || (SUBSTRING(bu.bet_score_result, 3, 3) = SUBSTRING(b.exact_score_result, 3, 3)), 5, 0)),
		 u.ranking_points = u.ranking_points + IF(bu.bet_score_result = b.exact_score_result, 15,
	                            IF((SUBSTRING(bu.bet_score_result, 1, 1) = SUBSTRING(b.exact_score_result, 1, 1)) || (SUBSTRING(bu.bet_score_result, 3, 3) = SUBSTRING(b.exact_score_result, 3, 3)), 5, 0))
	  WHERE b.bet_type = 1 AND u.id=bu.user_id;

	 
	/* Apuesta de Usuario - Type 2 de Ganador de Grupo */

	UPDATE bet_user_points AS bu INNER JOIN bets AS b ON bu.bet_id=b.id , d_users AS u
      SET 
	     bu.points = IF(bu.bet_group_result = b.exact_group_winner_result, 10, 0),
		 u.ranking_points = u.ranking_points + IF(bu.bet_group_result = b.exact_group_winner_result, 10, 0)
	   WHERE b.bet_type = 2 AND u.id=bu.user_id;
	
	
	UPDATE d_users AS u INNER JOIN (SELECT bu.user_id, COALESCE(SUM(bu.points), 0) AS upoints FROM bet_user_points bu GROUP BY bu.user_id) AS p  ON u.id=p.user_id SET u.ranking_points = upoints; 
	   
   END$$

DELIMITER ;

START TRANSACTION;

INSERT INTO `clients` (`id`, `name`, `email`, `logo_image`, `background_image`) VALUES (101, 'GEN LABS', 'info.genlabs@mailinator.com', 'https://cdn2.hubspot.net/hub/155206/file-416777316-jpg/images/hp_logo200.jpg', 'http://wallpapers3.hellowallpaper.com/other_windows-xp--01_26-1920x1440.jpg');
INSERT INTO `clients` (`id`, `name`, `email`, `logo_image`, `background_image`) VALUES (102, 'KODAK', 'info.kodak@mailinator.com', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Kodak_logo.svg/2000px-Kodak_logo.svg.png', 'http://cdn.shopify.com/s/files/1/0337/7469/products/9751354_Web__26659_1024x1024.jpeg?v=1403721168');

INSERT INTO `sectors` (`id`, `name`) VALUES
(1, 'RRHH'),
(2, 'Ventas'),
(3, 'Administracion'),
(4, 'Marketing'),
(5, 'IT'),
(6, 'Cobranzas'),
(7, 'Limpieza');

INSERT INTO `rewards` (`id`, `description`, `winner_position`, `custom_award_name`) VALUES
(1, 'PRIMER PREMIO', 1, NULL),
(2, 'SEGUNDO PREMIO', 2, NULL),
(3, 'TERCER PREMIO', 3, NULL);

INSERT INTO `user_settings` (`id`, `style_1_color`, `style_2_color`, `style_3_color`, `style_4_color`, `style_5_color`) VALUES (1, '#303030', '#353535', '#545454', '#E50000', '#FFFFFF');

INSERT INTO `d_users` (`id`, `firstname`, `lastname`, `username`, `password`, `enabled`, `pb_mobile_token`, `pb_platform`, `on_play`, `gender`, `age`, `ranking_points`, `profile_image`, `client_id`, `sector_id`, `reward_id`, `user_preferences_id`) VALUES
(1, 'Gabriel', 'Pasqualini', 'gabriel@portegno.com', 'Portegno@123', 1, NULL, NULL, 0, NULL, NULL, 0, 'https://www.undertrail.com/common/undertrail-bootstrap/dist/assets/img/navbar/avatar.png', NULL, NULL, NULL, NULL),
(2, 'Geronimo', 'Perez Salas', 'admin.genlabs@mailinator.com', 'Admin@123', 1, NULL, NULL, 0, 'M', 36, 0, 'https://www.undertrail.com/common/undertrail-bootstrap/dist/assets/img/navbar/avatar.png', 101, NULL, NULL, 1),
(3, 'Nicolas', 'Leguizamon', 'nico.genlabs@mailinator.com', 'Test123', 1, NULL, NULL, 1, 'M', 23, 0, 'https://www.undertrail.com/common/undertrail-bootstrap/dist/assets/img/navbar/avatar.png', 101, 4, NULL, 1),
(4, 'Eliseo', 'Cohen Imach', 'eli.genlabs@mailinator.com', 'Test123', 1, NULL, NULL, 1, 'M', 23, 0, 'https://www.undertrail.com/common/undertrail-bootstrap/dist/assets/img/navbar/avatar.png', 101, 5, NULL, 1),
(5, 'Hernan', 'Juarez', 'admin.kodak@mailinator.com', 'Admin@123', 1, NULL, NULL, 0, 'M', 34, 0,  'https://www.undertrail.com/common/undertrail-bootstrap/dist/assets/img/navbar/avatar.png', 102, NULL, NULL, 1),
(6, 'Juan', 'Fernandez', 'juan.kodak@mailinator.com', 'Test123', 1, NULL, NULL, 1, 'M', 27, 0, 'https://www.undertrail.com/common/undertrail-bootstrap/dist/assets/img/navbar/avatar.png', 102, 1, NULL, 1);

INSERT INTO `user_roles` (`id`, `rolename`, `user_id`) VALUES
(1, 'ROLE_SUPER_ADMIN', 1),
(2, 'ROLE_ADMIN', 2),
(3, 'ROLE_USER', 3),
(4, 'ROLE_USER', 4),
(5, 'ROLE_ADMIN', 5),
(6, 'ROLE_USER', 6);

/* SOCIAL CONTENT */
INSERT INTO `posts` (`id`, `posted_text`, `posted_date`, `user_id`, `is_comment`, `parent_post_id`) VALUES (1, 'Esta es una publicacion de prueba!', '2016-05-10 12:16:37', 4, 0, NULL);
INSERT INTO `posts` (`id`, `posted_text`, `posted_date`, `user_id`, `is_comment`, `parent_post_id`) VALUES (2, 'Esta es una publicacion de prueba!', '2016-05-11 09:26:37', 3, 0, NULL);
INSERT INTO `posts` (`id`, `posted_text`, `posted_date`, `user_id`, `is_comment`, `parent_post_id`) VALUES (3, 'Esta es una publicacion de prueba!', '2016-05-11 10:36:37', 3, 0, NULL);
INSERT INTO `posts` (`id`, `posted_text`, `posted_date`, `user_id`, `is_comment`, `parent_post_id`) VALUES (4, 'Esta es una publicacion de prueba!', '2016-05-11 12:46:37', 4, 0, NULL);
INSERT INTO `posts` (`id`, `posted_text`, `posted_date`, `user_id`, `is_comment`, `parent_post_id`) VALUES (5, 'Esta es una publicacion para los de RRHH solamente!', '2016-05-11 12:56:37', 2, 0, NULL);

INSERT INTO `posts` (`id`, `posted_text`, `posted_date`, `user_id`, `is_comment`, `parent_post_id`) VALUES (6, 'Esto es un comentario nomas...', '2016-05-12 12:16:37', 4, 1, 2);
INSERT INTO `posts` (`id`, `posted_text`, `posted_date`, `user_id`, `is_comment`, `parent_post_id`) VALUES (7, 'Esto es un comentario nomas...', '2016-05-12 12:17:37', 4, 1, 3);

INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (1, 1);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (1, 2);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (1, 3);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (1, 4);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (1, 5);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (1, 6);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (1, 7);

INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (2, 1);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (2, 2);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (2, 3);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (2, 4);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (2, 5);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (2, 6);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (2, 7);

INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (3, 1);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (3, 2);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (3, 3);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (3, 4);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (3, 5);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (3, 6);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (3, 7);

INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (4, 1);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (4, 2);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (4, 3);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (4, 4);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (4, 5);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (4, 6);
INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (4, 7);

INSERT INTO `posts_has_sectors` (`post_id`, `sector_id`) VALUES (5, 1);

INSERT INTO `likes` (`id`, `related_user_liked_id`, `related_post_id`, `notified_on_timeline`) VALUES (1, 4, 2, 0);
INSERT INTO `likes` (`id`, `related_user_liked_id`, `related_post_id`, `notified_on_timeline`) VALUES (2, 4, 3, 0);

INSERT INTO `tickets` (`id`, `ticket_type`, `title`, `description`, `posted_date`, `user_id`, `is_comment`, `parent_ticket_id`) VALUES (1, 1, 'PREMIO GANADOR DE JUEGO', 'Hola, quisiera saber cual es el premio para el ganador del juego...', '2016-05-15 08:00:00', 3, 0, NULL);
INSERT INTO `tickets` (`id`, `ticket_type`, `title`, `description`, `posted_date`, `user_id`, `is_comment`, `parent_ticket_id`) VALUES (2, 2, 'BOTON PARA SUBIR FOTO', 'Hola, el boton para subir una nueva imagen de perfil no me funciona...', '2016-05-15 08:00:00', 3, 0, NULL);
INSERT INTO `tickets` (`id`, `ticket_type`, `title`, `description`, `posted_date`, `user_id`, `is_comment`, `parent_ticket_id`) VALUES (3, 1, NULL, 'Si, el premio será un lavarropas..', '2016-05-15 08:00:00', 2, 1, 1);
INSERT INTO `tickets` (`id`, `ticket_type`, `title`, `description`, `posted_date`, `user_id`, `is_comment`, `parent_ticket_id`) VALUES (4, 2, NULL, 'Ok, lo revisaremos a la brevedad para solucionarlo..', '2016-05-15 08:00:00', 1, 1, 2);

/* FIXTURE DE JJOO */
INSERT INTO `teams` (`id`, `name`, `flag_img`, `shield_img`, `team_group`, `points_group`) VALUES
(1, 'BRASIL', 'http://img.fifa.com/images/flags/4/bra.png', 'http://thumb.resfu.com/img_data/escudos/medium/85587.jpg?size=60x', 1, 0),      
(2, 'DINAMARCA', 'http://img.fifa.com/images/flags/4/den.png', 'http://thumb.resfu.com/img_data/escudos/medium/86036.jpg?size=60x', 1, 0),   
(3, 'IRAK', 'http://img.fifa.com/images/flags/4/irq.png', 'http://thumb.resfu.com/img_data/escudos/medium/84113.jpg?size=60x', 1, 0),        
(4, 'SUDAFRICA', 'http://img.fifa.com/images/flags/4/rsa.png', 'http://thumb.resfu.com/img_data/escudos/medium/86034.jpg?size=60x', 1, 0),   
(5, 'COLOMBIA', 'http://img.fifa.com/images/flags/4/col.png', 'http://thumb.resfu.com/img_data/escudos/medium/85511.jpg?size=60x', 2, 0),    
(6, 'JAPON', 'http://img.fifa.com/images/flags/4/jpn.png', 'http://thumb.resfu.com/img_data/escudos/medium/84100.jpg?size=60x', 2, 0),       
(7, 'NIGERIA', 'http://img.fifa.com/images/flags/4/nga.png', 'http://thumb.resfu.com/img_data/escudos/medium/85596.jpg?size=60x', 2, 0),     
(8, 'SUECIA', 'http://img.fifa.com/images/flags/4/swe.png', 'http://thumb.resfu.com/img_data/escudos/medium/86032.jpg?size=60x', 2, 0),      
(9, 'ALEMANIA', 'http://img.fifa.com/images/flags/4/ger.png', 'http://thumb.resfu.com/img_data/escudos/medium/86037.jpg?size=60x', 3, 0),    
(10, 'COREA', 'http://img.fifa.com/images/flags/4/kor.png', 'http://thumb.resfu.com/img_data/escudos/medium/84103.jpg?size=60x', 3, 0),       
(11, 'FIYI', 'http://img.fifa.com/images/flags/4/fij.png', 'http://thumb.resfu.com/img_data/escudos/medium/86039.jpg?size=60x', 3, 0),       
(12, 'MEXICO', 'http://img.fifa.com/images/flags/4/mex.png', 'http://thumb.resfu.com/img_data/escudos/medium/77355.jpg?size=60x', 3, 0),     
(13, 'ARGELIA', 'http://img.fifa.com/images/flags/4/alg.png', 'http://thumb.resfu.com/img_data/escudos/medium/86035.jpg?size=60x', 4, 0),    
(14, 'ARGENTINA', 'http://img.fifa.com/images/flags/4/arg.png', 'http://thumb.resfu.com/img_data/escudos/medium/85593.jpg?size=60x', 4, 0),  
(15, 'HONDURAS', 'http://img.fifa.com/images/flags/4/hon.png', 'http://thumb.resfu.com/img_data/escudos/medium/77352.jpg?size=60x', 4, 0),   
(16, 'PORTUGAL', 'http://img.fifa.com/images/flags/4/por.png', 'http://thumb.resfu.com/img_data/escudos/medium/86033.jpg?size=60x', 4, 0);   

INSERT INTO `matches` (`id`, `local_team_id`, `visitor_team_id`, `start_date`, `playoff`, `description`, `local_score`, `visitor_score`, `has_extra_time`, `local_score_et`, `visitor_score_et`, `has_penalties`, `local_score_pen`, `visitor_score_pen`, `result_text`, `result_extra_text`, `winner`, `is_closed`) VALUES
(1, 3, 2, '2016-08-04 13:00:00', 0, 'PARTIDO GRUPO (F1)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(2, 15, 13, '2016-08-04 15:00:00', 0, 'PARTIDO GRUPO (F1)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(3, 1, 4, '2016-08-04 16:00:00', 0, 'PARTIDO GRUPO (F1)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(4, 12, 9, '2016-08-04 17:00:00', 0, 'PARTIDO GRUPO (F1)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(5, 16, 14, '2016-08-04 18:00:00', 0, 'PARTIDO GRUPO (F1)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(6, 8, 5, '2016-08-04 18:00:00', 0, 'PARTIDO GRUPO (F1)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(7, 11, 10, '2016-08-04 20:00:00', 0, 'PARTIDO GRUPO (F1)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(8, 7, 6, '2016-08-04 21:00:00', 0, 'PARTIDO GRUPO (F1)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),

(9, 11, 12, '2016-08-07 13:00:00', 0, 'PARTIDO GRUPO (F2)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(10, 15, 16, '2016-08-07 15:00:00', 0, 'PARTIDO GRUPO (F2)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(11, 9, 10, '2016-08-07 16:00:00', 0, 'PARTIDO GRUPO (F2)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(12, 14, 13, '2016-08-07 18:00:00', 0, 'PARTIDO GRUPO (F2)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(13, 8, 7, '2016-08-07 18:00:00', 0, 'PARTIDO GRUPO (F2)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(14, 2, 4, '2016-08-07 19:00:00', 0, 'PARTIDO GRUPO (F2)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(15, 6, 5, '2016-08-07 21:00:00', 0, 'PARTIDO GRUPO (F2)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(16, 1, 3, '2016-08-07 22:00:00', 0, 'PARTIDO GRUPO (F2)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),

(17, 13, 16, '2016-08-10 13:00:00', 0, 'PARTIDO GRUPO (F3)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(18, 14, 15, '2016-08-10 13:00:00', 0, 'PARTIDO GRUPO (F3)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(19, 9, 11, '2016-08-10 16:00:00', 0, 'PARTIDO GRUPO (F3)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(20, 10, 12, '2016-08-10 16:00:00', 0, 'PARTIDO GRUPO (F3)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(21, 5, 7, '2016-08-10 19:00:00', 0, 'PARTIDO GRUPO (F3)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(22, 6, 8, '2016-08-10 19:00:00', 0, 'PARTIDO GRUPO (F3)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(23, 2, 1, '2016-08-10 22:00:00', 0, 'PARTIDO GRUPO (F3)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(24, 4, 3, '2016-08-10 22:00:00', 0, 'PARTIDO GRUPO (F3)', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),

(25, NULL, NULL, '2016-08-13 13:00:00', 1, 'CUARTOS DE FINAL A', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(26, NULL, NULL, '2016-08-13 16:00:00', 1, 'CUARTOS DE FINAL B', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(27, NULL, NULL, '2016-08-13 19:00:00', 1, 'CUARTOS DE FINAL C', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(28, NULL, NULL, '2016-08-13 22:00:00', 1, 'CUARTOS DE FINAL D', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(29, NULL, NULL, '2016-08-17 13:00:00', 1, 'SEMIFINAL A', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(30, NULL, NULL, '2016-08-17 16:00:00', 1, 'SEMIFINAL B', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(31, NULL, NULL, '2016-08-20 13:00:00', 1, 'TERCER PUESTO', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0),
(32, NULL, NULL, '2016-08-20 17:30:00', 1, 'FINAL', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 'X-X', NULL, NULL, 0);

INSERT INTO `bets` (`id`, `bet_type`, `exact_score_result`, `exact_team_result`, `exact_group_winner_result`, `related_match_id`, `related_group_name`) VALUES
(1, 1, NULL, NULL, NULL, 1, NULL),
(2, 1, NULL, NULL, NULL, 2, NULL),
(3, 1, NULL, NULL, NULL, 3, NULL),
(4, 1, NULL, NULL, NULL, 4, NULL),
(5, 1, NULL, NULL, NULL, 5, NULL),
(6, 1, NULL, NULL, NULL, 6, NULL),
(7, 1, NULL, NULL, NULL, 7, NULL),
(8, 1, NULL, NULL, NULL, 8, NULL),
(9, 1, NULL, NULL, NULL, 9, NULL),
(10, 1, NULL, NULL, NULL, 10, NULL),
(11, 1, NULL, NULL, NULL, 11, NULL),
(12, 1, NULL, NULL, NULL, 12, NULL),
(13, 1, NULL, NULL, NULL, 13, NULL),
(14, 1, NULL, NULL, NULL, 14, NULL),
(15, 1, NULL, NULL, NULL, 15, NULL),
(16, 1, NULL, NULL, NULL, 16, NULL),
(17, 1, NULL, NULL, NULL, 17, NULL),
(18, 1, NULL, NULL, NULL, 18, NULL),
(19, 1, NULL, NULL, NULL, 19, NULL),
(20, 1, NULL, NULL, NULL, 20, NULL),
(21, 1, NULL, NULL, NULL, 21, NULL),
(22, 1, NULL, NULL, NULL, 22, NULL),
(23, 1, NULL, NULL, NULL, 23, NULL),
(24, 1, NULL, NULL, NULL, 24, NULL),
(25, 1, NULL, NULL, NULL, 25, NULL),
(26, 1, NULL, NULL, NULL, 26, NULL),
(27, 1, NULL, NULL, NULL, 27, NULL),
(28, 1, NULL, NULL, NULL, 28, NULL),
(29, 1, NULL, NULL, NULL, 29, NULL),
(30, 1, NULL, NULL, NULL, 30, NULL),
(31, 1, NULL, NULL, NULL, 31, NULL),
(32, 1, NULL, NULL, NULL, 32, NULL),
(33, 2, NULL, NULL, NULL, NULL, 1),
(34, 2, NULL, NULL, NULL, NULL, 2),
(35, 2, NULL, NULL, NULL, NULL, 3),
(36, 2, NULL, NULL, NULL, NULL, 4);

INSERT INTO `bet_user_points` (`bet_id`, `user_id`, `bet_team_result`, `bet_score_result`, `bet_group_result`, `points`, `notified_on_timeline`) VALUES (33, 3, NULL, NULL, 'BRASIL', NULL, 0);
INSERT INTO `bet_user_points` (`bet_id`, `user_id`, `bet_team_result`, `bet_score_result`, `bet_group_result`, `points`, `notified_on_timeline`) VALUES (34, 3, NULL, NULL, 'COLOMBIA', NULL, 0);
INSERT INTO `bet_user_points` (`bet_id`, `user_id`, `bet_team_result`, `bet_score_result`, `bet_group_result`, `points`, `notified_on_timeline`) VALUES (35, 3, NULL, NULL, 'ALEMANIA', NULL, 0);
INSERT INTO `bet_user_points` (`bet_id`, `user_id`, `bet_team_result`, `bet_score_result`, `bet_group_result`, `points`, `notified_on_timeline`) VALUES (36, 3, NULL, NULL, 'ARGENTINA', NULL, 0);

INSERT INTO `bet_user_points` (`bet_id`, `user_id`, `bet_team_result`, `bet_score_result`, `bet_group_result`, `points`, `notified_on_timeline`) VALUES (1, 3, 'LOCAL', '2-1', NULL, NULL, 0);
INSERT INTO `bet_user_points` (`bet_id`, `user_id`, `bet_team_result`, `bet_score_result`, `bet_group_result`, `points`, `notified_on_timeline`) VALUES (2, 3, 'EMPATE', '3-3', NULL, NULL, 0);
INSERT INTO `bet_user_points` (`bet_id`, `user_id`, `bet_team_result`, `bet_score_result`, `bet_group_result`, `points`, `notified_on_timeline`) VALUES (3, 3, 'VISITANTE', '0-3', NULL, NULL, 0);

INSERT INTO `bet_user_points` (`bet_id`, `user_id`, `bet_team_result`, `bet_score_result`, `bet_group_result`, `points`, `notified_on_timeline`) VALUES (1, 4, 'EMPATE', '2-2', NULL, NULL, 0);
INSERT INTO `bet_user_points` (`bet_id`, `user_id`, `bet_team_result`, `bet_score_result`, `bet_group_result`, `points`, `notified_on_timeline`) VALUES (2, 4, 'EMPATE', '3-3', NULL, NULL, 0);
INSERT INTO `bet_user_points` (`bet_id`, `user_id`, `bet_team_result`, `bet_score_result`, `bet_group_result`, `points`, `notified_on_timeline`) VALUES (3, 4, 'EMPATE', '0-0', NULL, NULL, 0);


COMMIT;
