UPDATE `TBL_EDITION` SET `T_START_UTC` = '2020-02-01 12:00:00', `T_END_UTC` = '2020-02-03 00:00:00', `N_ACTIVE` = b'1' WHERE `TBL_EDITION`.`N_ID_EDITION` = 1;

ALTER TABLE TBL_CONTEST_QSO ADD D_ENTITY_CODE INT NULL;
ALTER TABLE TBL_CONTEST_QSO ADD D_DXCC_NOT_FOUND INT NULL;

CREATE TABLE `CAT_DXCC_ENTITY` (
  `D_ENTITY_CODE` INT NOT NULL,
  `S_ENTITY` VARCHAR(255) NULL,
  `S_CONT` VARCHAR(45) NULL,
  `D_ITU` INT NULL,
  `D_CQ` INT NULL,
  `S_ORIGEN` VARCHAR(255) NULL,
  PRIMARY KEY (`D_ENTITY_CODE`));

ALTER TABLE TBL_CONTEST_QSO ADD FOREIGN KEY (D_ENTITY_CODE) REFERENCES CAT_DXCC_ENTITY(D_ENTITY_CODE);

CREATE TABLE `CAT_DXCC_SESSION` (
  `N_ID_DXCC_SESSION` INT NOT NULL,
  `S_KEY` VARCHAR(255) NULL,
  `N_COUNT` INT NULL,
  `D_SUB_EXP` DATETIME NULL,
  `D_GM_TIME` DATETIME NULL,
  `S_REMARK` VARCHAR(255) NULL,
  `S_ERROR` VARCHAR(255) NULL,
  PRIMARY KEY (`N_ID_DXCC_SESSION`));

ALTER TABLE CAT_DXCC_SESSION MODIFY N_ID_DXCC_SESSION INT NOT NULL AUTO_INCREMENT;

CREATE VIEW V_LAST_EMAIL AS(
    SELECT
        TBL_A.EDITION_ID,
        TBL_A.EMAIL_ID,
        LOG.N_ID_CONTEST_LOG,
        TBL_A.EMAIL_SUBJECT,
        TBL_A.EMAIL_STATUS
    FROM
        TBL_CONTEST_LOG LOG
    INNER JOIN(
        SELECT
            EMAIL.S_SUBJECT EMAIL_SUBJECT,
            MAX(EMAIL.N_ID_EMAIL) AS EMAIL_ID,
            ST.S_STATUS EMAIL_STATUS,
            EDITION.N_ID_EDITION EDITION_ID
        FROM
            TBL_EMAIL EMAIL
        INNER JOIN CAT_EMAIL_STATUS ST ON
            ST.N_ID_EMAIL_STATUS = EMAIL.N_ID_EMAIL_STATUS
        INNER JOIN TBL_EDITION EDITION ON
            EDITION.N_ID_EDITION = EMAIL.N_ID_EDITION
        WHERE
            EMAIL.S_SUBJECT <> '' AND EMAIL.S_SUBJECT IS NOT NULL AND EMAIL.VERIFIED_AT IS NOT NULL AND EMAIL.ANSWERED_AT IS NOT NULL AND ST.S_STATUS = 'PARSED'
        GROUP BY
            EMAIL.S_SUBJECT,
            EDITION.N_ID_EDITION
    ) TBL_A
ON
    TBL_A.EMAIL_ID = LOG.N_ID_EMAIL
);

ALTER TABLE TBL_CONTEST_LOG ADD D_ENTITY_CODE INT NULL;
ALTER TABLE TBL_CONTEST_LOG ADD D_DXCC_NOT_FOUND INT NULL;

ALTER TABLE TBL_CONTEST_LOG ADD FOREIGN KEY (D_ENTITY_CODE) REFERENCES CAT_DXCC_ENTITY(D_ENTITY_CODE);

DELETE FROM `CAT_EMAIL_STATUS` WHERE `CAT_EMAIL_STATUS`.`N_ID_EMAIL_STATUS` = 8;
DELETE FROM `CAT_EMAIL_STATUS` WHERE `CAT_EMAIL_STATUS`.`N_ID_EMAIL_STATUS` = 7;
DELETE FROM `CAT_EMAIL_STATUS` WHERE `CAT_EMAIL_STATUS`.`N_ID_EMAIL_STATUS` = 6;

DELETE FROM `REL_EMAIL_EMAIL_ERROR` WHERE `REL_EMAIL_EMAIL_ERROR`.`N_ID_EMAIL` > 15;
DELETE FROM `TBL_CONTEST_QSO` WHERE `TBL_CONTEST_QSO`.`N_ID_CONTEST_LOG` IN (SELECT `N_ID_CONTEST_LOG` FROM `TBL_CONTEST_LOG` WHERE `N_ID_EMAIL` > 15);
DELETE FROM `TBL_CONTEST_LOG` WHERE `N_ID_EMAIL` > 15;
DELETE FROM `TBL_ATTACHED_FILE` WHERE `TBL_ATTACHED_FILE`.`N_ID_EMAIL` > 15;
DELETE FROM `TBL_EMAIL` WHERE `TBL_EMAIL`.`N_ID_EMAIL` > 15;

-- START CAT_QSO_ERROR
CREATE TABLE `CAT_QSO_ERROR` (
  `N_ID_QSO_ERROR` int(11) NOT NULL,
  `N_ID_EDITION` int(11) DEFAULT NULL,
  `S_KEY` varchar(255) DEFAULT NULL,
  `S_DESCRIPCION` varchar(255) DEFAULT NULL
);

ALTER TABLE `CAT_QSO_ERROR`
  ADD PRIMARY KEY (`N_ID_QSO_ERROR`),
  ADD KEY `CONSTRAINT_FK_CAT_QSO_ERROR` (`N_ID_EDITION`);

ALTER TABLE `CAT_QSO_ERROR`
  MODIFY `N_ID_QSO_ERROR` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `CAT_QSO_ERROR`
  ADD CONSTRAINT `CONSTRAINT_FK_CAT_EDITION` FOREIGN KEY (`N_ID_EDITION`) REFERENCES `TBL_EDITION` (`N_ID_EDITION`);

INSERT INTO `CAT_QSO_ERROR` (`N_ID_QSO_ERROR`, `N_ID_EDITION`, `S_KEY`, `S_DESCRIPCION`) VALUES ('1', '1', 'QSO_MADE_BEFORE_CONTEST_START', 'The QSO was made before the contest start');
INSERT INTO `CAT_QSO_ERROR` (`N_ID_QSO_ERROR`, `N_ID_EDITION`, `S_KEY`, `S_DESCRIPCION`) VALUES ('2', '1', 'QSO_MADE_AFTER_CONTEST_START', 'The QSO was made after the contest start');
INSERT INTO `CAT_QSO_ERROR` (`N_ID_QSO_ERROR`, `N_ID_EDITION`, `S_KEY`, `S_DESCRIPCION`) VALUES ('3', '1', 'QSO_OUT_OF_BAND', 'The qso was made out of frequency on a band not allowed');
INSERT INTO `CAT_QSO_ERROR` (`N_ID_QSO_ERROR`, `N_ID_EDITION`, `S_KEY`, `S_DESCRIPCION`) VALUES ('4', '1', 'NOT_VALID_EXCHANGE_EMMITED', 'The exchange emmited is invalid');
INSERT INTO `CAT_QSO_ERROR` (`N_ID_QSO_ERROR`, `N_ID_EDITION`, `S_KEY`, `S_DESCRIPCION`) VALUES ('5', '1', 'QSO_ON_WARC_BAND', 'The was made on a prohibited WARC band');
-- END CAT_QSO_ERROR

-- START CAT_FREQUENCY_BAND
CREATE TABLE `CAT_FREQUENCY_BAND` (
  `N_ID_FREQUENCY_BAND` int(11) NOT NULL,
  `S_BAND` varchar(255) DEFAULT NULL,
  `D_START_FREQ` decimal(12, 4) DEFAULT NULL,
  `D_END_FREQ` decimal(12, 4) DEFAULT NULL,
  `S_REMARK` varchar(255) DEFAULT NULL
);

ALTER TABLE `CAT_FREQUENCY_BAND`
  ADD PRIMARY KEY (`N_ID_FREQUENCY_BAND`);

ALTER TABLE `CAT_FREQUENCY_BAND`
  MODIFY `N_ID_FREQUENCY_BAND` int(11) NOT NULL AUTO_INCREMENT;
-- END CAT_FREQUENCY_BAND

insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('1', '160 meters', '1.8', '2', 'CW');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('2', '160 meters', '1.8', '1.81', 'Digital Modes');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('3', '160 meters', '1.81', '1.81', 'CW QRP');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('4', '160 meters', '1.843', '2', 'SSB, SSTV and other wideband modes');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('5', '160 meters', '1.91', '1.91', 'SSB QRP');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('6', '160 meters', '1.995', '2', 'Experimental');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('7', '160 meters', '1.999', '2', 'Beacons');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('8', '80 meters', '3.59', '3.59', 'RTTY/Data DX');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('9', '80 meters', '3.57', '3.6', 'RTTY/Data');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('10', '80 meters', '3.79', '3.8', 'DX window');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('11', '80 meters', '3.845', '3.845', 'SSTV');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('12', '80 meters', '3.885', '3.885', 'AM calling frequency');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('13', '60 meters', '5.3305', '5.3305', 'USB phone1 and CW/RTTY/data2');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('14', '60 meters', '5.3465', '5.3465', 'USB phone1 and CW/RTTY/data2');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('15', '60 meters', '5.357', '5.357', 'USB phone1 and CW/RTTY/data2');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('16', '60 meters', '5.3715', '5.3715', 'USB phone1 and CW/RTTY/data2');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('17', '60 meters', '5.4035', '5.4035', 'USB phone1 and CW/RTTY/data2');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('18', '40 meters', '7.04', '0.04', 'RTTY/Data DX');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('19', '40 meters', '7.08', '7.125', 'RTTY/Data');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('20', '40 meters', '7.171', '7.171', 'SSTV');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('21', '40 meters', '7.29', '7.29', 'AM calling frequency');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('22', '30 meters', '10.13', '10.14', 'RTTY');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('23', '30 meters', '10.14', '10.15', 'Packet');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('24', '20 meters', '14.07', '14.095', 'RTTY');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('25', '20 meters', '14.095', '14.0995', 'Packet');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('26', '20 meters', '14.1', '14.1', 'NCDXF Beacons');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('27', '20 meters', '14.1005', '14.112', 'Packet');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('28', '20 meters', '14.23', '14.23', 'SSTV');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('29', '20 meters', '14.286', '14.286', 'AM calling frequency');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('30', '17 meters', '18.1', '18.105', 'RTTY');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('31', '17 meters', '18.105', '18.11', 'Packet');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('32', '15 meters', '21.07', '21.11', 'RTTY/Data');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('33', '15 meters', '21.34', '21.34', 'SSTV');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('34', '12 meters', '24.92', '24.925', 'RTTY');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('35', '12 meters', '24.925', '24.93', 'Packet');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('36', '10 meters', '28', '28.07', 'CW');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('37', '10 meters', '28.07', '28.15', 'RTTY');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('38', '10 meters', '28.15', '28.19', 'CW');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('39', '10 meters', '28.2', '28.3', 'Beacons');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('40', '10 meters', '28.3', '29.3', 'Phone');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('41', '10 meters', '28.68', '28.68', 'SSTV');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('42', '10 meters', '29', '29.2', 'AM');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('43', '10 meters', '29.3', '29.51', 'Satellite Downlinks');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('44', '10 meters', '29.52', '29.59', 'Repeater Inputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('45', '10 meters', '29.6', '29.6', 'FM Simplex');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('46', '10', '29.61', '29.7', 'Repeater Outputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('47', '6 meters', '50', '50.1', 'CW, beacons');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('48', '6 meters', '50.06', '50.08', 'beacon subband');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('49', '6 meters', '50.1', '50.3', 'SSB, CW');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('50', '6 meters', '50.1', '50.125', 'DX window');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('51', '6 meters', '50.125', '50.125', 'SSB calling');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('52', '6 meters', '50.3', '50.6', 'All modes');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('53', '6 meters', '50.6', '50.8', 'Nonvoice communications');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('54', '6 meters', '50.62', '50.62', 'Digital (packet) calling');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('55', '6 meters', '50.8', '51', 'Radio remote control (20-kHz channels)');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('56', '6 meters', '51', '51.1', 'Pacific DX window');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('57', '6 meters', '51.12', '51.48', 'Repeater inputs (19 channels)');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('58', '6 meters', '51.12', '51.18', 'Digital repeater inputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('59', '6 meters', '51.5', '51.6', '');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('60', '2 meters', '144', '144.05', 'EME (CW)');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('61', '2 meters', '144.05', '144.1', 'General CW and weak signals');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('62', '2 meters', '144.1', '144.2', 'EME and weak-signal SSB');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('63', '2 meters', '144.2', '144.2', 'National calling frequency');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('64', '2 meters', '144.2', '144.275', 'General SSB operation');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('65', '2 meters', '144.275', '144.3', 'Propagation beacons');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('66', '2 meters', '144.3', '144.5', 'New OSCAR subband');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('67', '2 meters', '144.5', '144.6', 'Linear translator inputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('68', '2 meters', '144.6', '144.9', 'FM repeater inputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('69', '2 meters', '144.9', '145.1', 'Weak signal and FM simplex (145.01,03,05,07,09 are widely used for packet)');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('70', '2 meters', '145.1', '145.2', 'Linear translator outputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('71', '2 meters', '145.2', '145.5', 'FM repeater outputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('72', '2 meters', '145.5', '145.8', 'Miscellaneous and experimental modes');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('73', '2 meters', '145.8', '146', 'OSCAR subband');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('74', '2 meters', '146.01', '146.37', 'Repeater inputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('75', '2 meters', '146.4', '146.58', 'Simplex');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('76', '2 meters', '146.52', '146.52', 'National Simplex Calling Frequency');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('77', '2 meters', '146.61', '146.97', 'Repeater outputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('78', '2 meters', '147', '147.39', 'Repeater outputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('79', '2 meters', '147.42', '147.57', 'Simplex');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('80', '2 meters', '147.6', '147.99', 'Repeater inputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('81', '1.25  meters', '222', '222.15', 'Weak-signal modes');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('82', '1.25 meters', '222', '222.025', 'EME');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('83', '1.25 meters', '222.05', '222.06', 'Propagation beacons');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('84', '1.25 meters', '222.1', '222.1', 'SSB & CW calling frequency');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('85', '1.25 meters', '222.1', '222.15', 'Weak-signal CW & SSB');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('86', '1.25 meters', '222.15', '222.25', 'Local coordinator\'s option; weak signal, ACSB, repeater inputs, control');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('87', '1.25 meters', '222.25', '223.38', 'FM repeater inputs only');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('88', '1.25 meters', '223.4', '223.52', 'FM simplex');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('89', '1.25 meters', '223.52', '223.64', 'Digital, packet');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('90', '1.25 meters', '223.64', '223.7', 'Links, control');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('91', '1.25 meters', '223.71', '223.85', 'Local coordinator\'s option; FM simplex, packet, repeater outputs');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('92', '1.25 meters', '223.85', '224.98', 'Repeater outputs only');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('93', '70 centimeters', '420', '426', 'ATV repeater or simplex with 421.25 MHz video carrier control links and experimental');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('94', '70 centimeters', '426', '432', 'ATV simplex with 427.250-MHz video carrier frequency');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('95', '70 centimeters', '432', '432.07', 'EME (Earth-Moon-Earth)');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('96', '70 centimeters', '432.07', '432.1', 'Weak-signal CW');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('97', '70 centimeters', '432.1', '70', 'cm calling frequency');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('98', '70 centimeters', '432.1', '432.3', 'Mixed-mode and weak-signal work');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('99', '70 centimeters', '432.3', '432.4', 'Propagation beacons');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('100', '70 centimeters', '432.4', '433', 'Mixed-signal work');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('101', '70 centimeters', '433', '435', 'Auxiliary/repeater links');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('102', '70 centimeters', '435', '438', 'Satellite only (internationally)');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('103', '70 centimeters', '438', '444', 'ATV repeater input with 439.250-MHz video carrier frequency and repeater links');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('104', '70 centimeters', '442', '445', 'Repeater inputs and outputs (local option)');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('105', '70 centimeters', '445', '447', 'Shared by auxiliary and control links, repeaters and simplex (local option)');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('106', '70 centimeters', '446', '446', 'National simplex frequency');
insert into CAT_FREQUENCY_BAND (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) values ('107', '70 centimeters', '447', '450', 'Repeater inputs and outputs (local option)');

ALTER TABLE TBL_CONTEST_QSO ADD N_ID_FREQUENCY_BAND INT NULL;
ALTER TABLE `TBL_CONTEST_QSO`
  ADD CONSTRAINT `CONSTRAINT_FK_FREQUENCY_BAND` FOREIGN KEY (`N_ID_FREQUENCY_BAND`) REFERENCES `CAT_FREQUENCY_BAND` (`N_ID_FREQUENCY_BAND`);

ALTER TABLE `TBL_EDITION` ADD `S_QSO_VALIDATION_IMPL` VARCHAR(255) NULL DEFAULT NULL AFTER `S_QSO_PARSER_IMPL`;
UPDATE `TBL_EDITION` SET `N_ACTIVE` = b'1', `S_QSO_VALIDATION_IMPL` = 'evaluateQsoRtty2020' WHERE `TBL_EDITION`.`N_ID_EDITION` = 1;

CREATE TABLE `TBL_CONTEO` ( `N_ID_CONTEO` INT NOT NULL AUTO_INCREMENT , `S_DESCRIPTION` VARCHAR(255) NULL , `S_DATETIME` DATETIME NOT NULL , `N_ID_EDITION` INT NOT NULL , PRIMARY KEY
 (`N_ID_CONTEO`));

ALTER TABLE `TBL_CONTEO`
  ADD CONSTRAINT `CONSTRAINT_FK_EDITION` FOREIGN KEY (`N_ID_EDITION`) REFERENCES `TBL_EDITION` (`N_ID_EDITION`);


CREATE TABLE `TBL_QSO_CONTEO` (`N_ID_REL_QSO_CONTEO` INT NOT NULL AUTO_INCREMENT, `N_ID_CONTEST_QSO` INT NULL , `N_ID_CONTEO` INT NULL , `D_DATETIME` DATETIME NOT NULL, `N_POINTS` INT NULL , `IS_COMPLETE` INT(1) NOT NULL , PRIMARY KEY (`N_ID_REL_QSO_CONTEO`));
ALTER TABLE `TBL_QSO_CONTEO` ADD `N_IS_MULTIPLY` BOOLEAN NULL DEFAULT NULL AFTER `IS_COMPLETE`;

ALTER TABLE `TBL_QSO_CONTEO` ADD CONSTRAINT `CONSTRAINT_FK_CONTEST_QSO` FOREIGN KEY (`N_ID_CONTEST_QSO`) REFERENCES `TBL_CONTEST_QSO` (`N_ID_CONTEST_QSO`);
ALTER TABLE `TBL_QSO_CONTEO` ADD CONSTRAINT `CONSTRAINT_FK_CONTEO` FOREIGN KEY (`N_ID_CONTEO`) REFERENCES `TBL_CONTEO` (`N_ID_CONTEO`);


CREATE TABLE `REL_QSO_CONTEO_QSO_ERROR` (`ID_REL_QSO_CONTEO_QSO_ERROR` INT NOT NULL AUTO_INCREMENT, `N_ID_REL_QSO_CONTEO` INT NULL, `N_ID_QSO_ERROR` INT NULL , `D_DATETIME` DATETIME NULL, PRIMARY KEY (`ID_REL_QSO_CONTEO_QSO_ERROR`));
ALTER TABLE `REL_QSO_CONTEO_QSO_ERROR` ADD CONSTRAINT `CONSTRAINT_FK_REL_QSO_CONTEO` FOREIGN KEY (`N_ID_REL_QSO_CONTEO`) REFERENCES `TBL_QSO_CONTEO` (`N_ID_REL_QSO_CONTEO`);
ALTER TABLE `REL_QSO_CONTEO_QSO_ERROR` ADD CONSTRAINT `CONSTRAINT_FK_QSO_ERROR` FOREIGN KEY (`N_ID_QSO_ERROR`) REFERENCES `CAT_QSO_ERROR` (`N_ID_QSO_ERROR`);

CREATE TABLE `REL_CONTEO_CONTEST_LOG` ( `N_ID_REL_CONTEO_CONTEST_LOG` INT NOT NULL AUTO_INCREMENT , `N_ID_CONTEO` INT NOT NULL , `N_ID_CONTEST_LOG` INT NOT NULL , `N_SUM_OF_POINTS` INT NOT NULL , `N_MULTIPLIERS` INT NOT NULL , `N_TOTAL_POINTS` INT NOT NULL , `IS_COMPLETE` BOOLEAN NOT NULL , PRIMARY KEY (`N_ID_REL_CONTEO_CONTEST_LOG`));
ALTER TABLE `REL_CONTEO_CONTEST_LOG` ADD CONSTRAINT `CONSTRAINT_FK_REL_CONTEO_CONTEST_LOG_CONTEO` FOREIGN KEY (`N_ID_CONTEO`) REFERENCES `TBL_CONTEO` (`N_ID_CONTEO`);
ALTER TABLE `REL_CONTEO_CONTEST_LOG` ADD CONSTRAINT `CONSTRAINT_FK_REL_CONTEO_CONTEST_LOG_CONTEST_LOG` FOREIGN KEY (`N_ID_CONTEST_LOG`) REFERENCES `TBL_CONTEST_LOG` (`N_ID_CONTEST_LOG`);