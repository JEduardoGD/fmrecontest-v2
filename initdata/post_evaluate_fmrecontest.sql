-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Servidor: mysql-docker
-- Tiempo de generación: 18-02-2020 a las 12:30:08
-- Versión del servidor: 5.7.28
-- Versión de PHP: 7.2.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `fmrecontest`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `CAT_DXCC_ENTITY`
--

CREATE TABLE `CAT_DXCC_ENTITY` (
  `D_ENTITY_CODE` int(11) NOT NULL,
  `S_ENTITY` varchar(255) DEFAULT NULL,
  `S_CONT` varchar(45) DEFAULT NULL,
  `D_ITU` int(11) DEFAULT NULL,
  `D_CQ` int(11) DEFAULT NULL,
  `S_ORIGEN` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `CAT_DXCC_ENTITY`
--

INSERT INTO `CAT_DXCC_ENTITY` (`D_ENTITY_CODE`, `S_ENTITY`, `S_CONT`, `D_ITU`, `D_CQ`, `S_ORIGEN`) VALUES
(1, 'Canada', NULL, 9, 5, 'QRZ.com'),
(15, 'Russia', NULL, 30, 16, 'QRZ.com'),
(27, 'Belarus', NULL, 29, 16, 'QRZ.com'),
(29, 'Canary Islands', NULL, 36, 33, 'QRZ.com'),
(40, 'Greece', NULL, 28, 20, 'QRZ.com'),
(46, 'East Malaysia', NULL, 54, 28, 'QRZ.com'),
(50, 'Mexico', NULL, NULL, NULL, 'QRZ.com'),
(54, 'European Russia', 'EU', 19, 16, 'Puebla DX'),
(70, 'Cuba', NULL, 11, 8, 'QRZ.com'),
(75, 'Georgia', NULL, NULL, NULL, 'QRZ.com'),
(76, 'Guatemala', NULL, 11, 7, 'QRZ.com'),
(100, 'Argentina', NULL, 14, 13, 'QRZ.com'),
(106, 'Guernsey', NULL, 27, 14, 'QRZ.com'),
(126, 'Kaliningrad', NULL, 29, 15, 'QRZ.com'),
(130, 'Kazakhstan', NULL, NULL, NULL, 'QRZ.com'),
(145, 'Latvia', NULL, 29, 15, 'QRZ.com'),
(146, 'Lithuania', NULL, 29, 15, 'QRZ.com'),
(148, 'Venezuela', NULL, 12, 9, 'QRZ.com'),
(150, 'Australia', NULL, 59, 30, 'QRZ.com'),
(179, 'Moldova', NULL, 29, 16, 'QRZ.com'),
(202, 'United States', NULL, 11, 8, 'QRZ.com'),
(206, 'Netherlands', NULL, 28, 15, 'QRZ.com'),
(209, 'Belgium', 'Eu', 27, 14, 'Puebla DX'),
(212, 'Bulgaria', NULL, NULL, NULL, 'QRZ.com'),
(221, 'Denmark', NULL, NULL, NULL, 'QRZ.com'),
(223, 'England', NULL, NULL, NULL, 'QRZ.com'),
(224, 'Finland', NULL, 18, 15, 'QRZ.com'),
(227, 'France', NULL, NULL, NULL, 'QRZ.com'),
(230, 'Germany', NULL, 28, 14, 'QRZ.com'),
(236, 'Greece', NULL, 28, 20, 'QRZ.com'),
(239, 'Hungary', 'Eu', 28, 15, 'Puebla DX'),
(248, 'Italy', NULL, 28, 15, 'QRZ.com'),
(263, 'Netherlands', NULL, NULL, NULL, 'QRZ.com'),
(265, 'Northern Ireland', NULL, NULL, NULL, 'QRZ.com'),
(266, 'Norway', NULL, 18, 14, 'QRZ.com'),
(269, 'Poland', NULL, NULL, NULL, 'QRZ.com'),
(275, 'Romania', NULL, 28, 20, 'QRZ.com'),
(279, 'Scotland', NULL, 27, 14, 'QRZ.com'),
(281, 'Spain', NULL, NULL, NULL, 'QRZ.com'),
(284, 'Sweden', NULL, 18, 14, 'QRZ.com'),
(287, 'Switzerland', NULL, 28, 14, 'QRZ.com'),
(288, 'Ukraine', NULL, 29, 16, 'QRZ.com'),
(291, 'United States', NULL, NULL, NULL, 'QRZ.com'),
(296, 'Clipperton Island', NULL, NULL, NULL, 'QRZ.com'),
(299, 'West Malaysia', NULL, 54, 28, 'QRZ.com'),
(318, 'China', NULL, 44, 24, 'QRZ.com'),
(321, 'Hong Kong', NULL, 44, 24, 'QRZ.com'),
(324, 'India', NULL, 41, 22, 'QRZ.com'),
(327, 'Indonesia', NULL, 54, 28, 'QRZ.com'),
(339, 'Japan', NULL, 45, 25, 'QRZ.com'),
(363, 'Mongolia', NULL, 32, 23, 'QRZ.com'),
(375, 'Philippines', NULL, NULL, NULL, 'QRZ.com'),
(376, 'Qatar', NULL, 39, 21, 'QRZ.com'),
(387, 'Thailand', NULL, 49, 26, 'QRZ.com'),
(390, 'Turkey', NULL, 39, 20, 'QRZ.com'),
(497, 'Croatia', NULL, 28, 15, 'QRZ.com'),
(499, 'Slovenia', NULL, 28, 15, 'QRZ.com'),
(501, 'Bosnia and Herzegovina', NULL, 28, 15, 'QRZ.com'),
(502, 'North Macedonia (Republic of)', NULL, 28, 15, 'QRZ.com'),
(503, 'Czech Republic', NULL, NULL, NULL, 'QRZ.com'),
(504, 'Slovak Republic', NULL, 28, 15, 'QRZ.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `CAT_DXCC_SESSION`
--

CREATE TABLE `CAT_DXCC_SESSION` (
  `N_ID_DXCC_SESSION` int(11) NOT NULL,
  `S_KEY` varchar(255) DEFAULT NULL,
  `N_COUNT` int(11) DEFAULT NULL,
  `D_SUB_EXP` datetime DEFAULT NULL,
  `D_GM_TIME` datetime DEFAULT NULL,
  `S_REMARK` varchar(255) DEFAULT NULL,
  `S_ERROR` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `CAT_DXCC_SESSION`
--

INSERT INTO `CAT_DXCC_SESSION` (`N_ID_DXCC_SESSION`, `S_KEY`, `N_COUNT`, `D_SUB_EXP`, `D_GM_TIME`, `S_REMARK`, `S_ERROR`) VALUES
(1, '4e69dbf84375e7cd5effd23f12cf32de', 13920, '2021-01-22 06:00:00', '2020-01-18 18:29:01', 'cpu: 0.155s', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `CAT_EMAIL_ERROR`
--

CREATE TABLE `CAT_EMAIL_ERROR` (
  `N_ID_CAT_EMAIL_ERROR` int(11) NOT NULL,
  `N_ID_EDITION` int(11) DEFAULT NULL,
  `S_DESCRIPCION` varchar(255) DEFAULT NULL,
  `S_SUGGESTION_ES` varchar(600) DEFAULT NULL,
  `S_SUGGESTION_EN` varchar(600) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `CAT_EMAIL_ERROR`
--

INSERT INTO `CAT_EMAIL_ERROR` (`N_ID_CAT_EMAIL_ERROR`, `N_ID_EDITION`, `S_DESCRIPCION`, `S_SUGGESTION_ES`, `S_SUGGESTION_EN`) VALUES
(1, 1, 'EMAIL_WITHOUT_SUBJECT', 'Su correo electrónico no contiene asunto, recuerde incluir el asunto de su correo electrónico (que debería ser solo su indicativo) y reenvie.', 'Your email has no subject, please remember to include the subject of your email (It must be your callsign only) and resend.'),
(2, 1, 'EMAIL_WITHOUT_ATTACHED_FILES', 'Su correo electrónico no contiene ningún archivo adjunto, recuerde adjuntar su archivo de log y reenvie.', 'Your email has no attached file, please your logfile in Cabrillo format and resend it.'),
(3, 1, 'CANT_IDENTIFY_LOGFILE', 'Su correo electrónico contiene algunos archivos adjuntos pero no podemos determinar cuál es su archivo de log, recuerde que su archivo de log debe nombrarse con una extensión .txt, .log o .cbr, cambie el nombre y adjunte su archivo de log y vuelva a envielo.', 'Your has attached files but we can\'t determine wich one is your logfile, remember that your log file should be named with a .txt, .log or .cbr extension, please rename and attach your logfile an resend it again.'),
(4, 1, 'MANY_POSSIBLE_LOGFILE', 'Su correo electrónico contiene algunos archivos adjuntos, pero no podemos determinar cuál es su archivo de log porque mas de uno de ellos podría ser, recuerde que su archivo de log debe nombrarse con una extensión .txt, .log o .cbr, por favor verifique esto y reenvie.', 'Your email contains some attached files but we cant to determine wich one is your logfile because many of that can be, remember that your log file should be named with a .txt, .log or .cbr extension, please rename and attach your logfile an resend again.'),
(5, 1, 'SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN', 'El asunto en su correo electrónico no es igual al indicativo en su log (que fue tomado de su archivo de log) recuerde que su asunto debe ser igual a su indicativo en su log Por favor, corríjalo y reenvíelo.', 'The subject on your Email must be your callsign only, no other text is accepted as stated on the rules. Please correct and resend.'),
(6, 1, 'EMAIL_WITHOUT_CONTSTLOG', 'Pudimos encontrar un posible archivo de log pero no pudimos analizarlo. Compruebe que su archivo de log se generó en formato cabrillo y vuelva a enviarlo.', 'The system found a file, however is unable to parse it. Please make sure your log was created on Cabrillo format, correct and resend it'),
(7, 1, 'SUBJECT_WITH_MORE_THAN_ONE_WORD', 'El asunto de su correo contiene más de una palabra, recuerde incluir el asunto de su correo electrónico (que debería ser solo su indicativo) y vuelva a enviarlo.', 'Your email subject has more than one word; the subject must be your callsign only. Correct and resend.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `CAT_EMAIL_STATUS`
--

CREATE TABLE `CAT_EMAIL_STATUS` (
  `N_ID_EMAIL_STATUS` int(11) NOT NULL,
  `S_STATUS` varchar(255) DEFAULT NULL,
  `S_DESCRIPTION` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `CAT_EMAIL_STATUS`
--

INSERT INTO `CAT_EMAIL_STATUS` (`N_ID_EMAIL_STATUS`, `S_STATUS`, `S_DESCRIPTION`) VALUES
(1, 'RECIVED', 'When email has recived is setted with this status'),
(2, 'IGNORED', 'The subject contains the word \'Undelivered\' because that this email will be ignored'),
(3, 'IDENTIFIED', 'The email contains a file identified as a logfile'),
(4, 'NO_IDENTIFIED', 'The logfile cannot be identified as a logfile'),
(5, 'PARSED', 'The logfile was succesfully parsed into log and qsos registers');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `CAT_FREQUENCY_BAND`
--

CREATE TABLE `CAT_FREQUENCY_BAND` (
  `N_ID_FREQUENCY_BAND` int(11) NOT NULL,
  `S_BAND` varchar(255) DEFAULT NULL,
  `D_START_FREQ` decimal(12,4) DEFAULT NULL,
  `D_END_FREQ` decimal(12,4) DEFAULT NULL,
  `S_REMARK` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `CAT_FREQUENCY_BAND`
--

INSERT INTO `CAT_FREQUENCY_BAND` (`N_ID_FREQUENCY_BAND`, `S_BAND`, `D_START_FREQ`, `D_END_FREQ`, `S_REMARK`) VALUES
(1, '160 meters', '1.8000', '2.0000', 'CW'),
(2, '160 meters', '1.8000', '1.8100', 'Digital Modes'),
(3, '160 meters', '1.8100', '1.8100', 'CW QRP'),
(4, '160 meters', '1.8430', '2.0000', 'SSB, SSTV and other wideband modes'),
(5, '160 meters', '1.9100', '1.9100', 'SSB QRP'),
(6, '160 meters', '1.9950', '2.0000', 'Experimental'),
(7, '160 meters', '1.9990', '2.0000', 'Beacons'),
(8, '80 meters', '3.5900', '3.5900', 'RTTY/Data DX'),
(9, '80 meters', '3.5700', '3.6000', 'RTTY/Data'),
(10, '80 meters', '3.7900', '3.8000', 'DX window'),
(11, '80 meters', '3.8450', '3.8450', 'SSTV'),
(12, '80 meters', '3.8850', '3.8850', 'AM calling frequency'),
(13, '60 meters', '5.3305', '5.3305', 'USB phone1 and CW/RTTY/data2'),
(14, '60 meters', '5.3465', '5.3465', 'USB phone1 and CW/RTTY/data2'),
(15, '60 meters', '5.3570', '5.3570', 'USB phone1 and CW/RTTY/data2'),
(16, '60 meters', '5.3715', '5.3715', 'USB phone1 and CW/RTTY/data2'),
(17, '60 meters', '5.4030', '5.4035', 'USB phone1 and CW/RTTY/data2'),
(18, '40 meters', '7.0400', '0.0400', 'RTTY/Data DX'),
(19, '40 meters', '7.0800', '7.1250', 'RTTY/Data'),
(20, '40 meters', '7.1710', '7.1710', 'SSTV'),
(21, '40 meters', '7.2900', '7.2900', 'AM calling frequency'),
(22, '30 meters', '10.1300', '10.1400', 'RTTY'),
(23, '30 meters', '10.1400', '10.1500', 'Packet'),
(24, '20 meters', '14.0700', '14.0950', 'RTTY'),
(25, '20 meters', '14.0950', '14.0995', 'Packet'),
(26, '20 meters', '14.1000', '14.1000', 'NCDXF Beacons'),
(27, '20 meters', '14.1005', '14.1120', 'Packet'),
(28, '20 meters', '14.2300', '14.2300', 'SSTV'),
(29, '20 meters', '14.2860', '14.2860', 'AM calling frequency'),
(30, '17 meters', '18.1000', '18.1050', 'RTTY'),
(31, '17 meters', '18.1050', '18.1100', 'Packet'),
(32, '15 meters', '21.0700', '21.1100', 'RTTY/Data'),
(33, '15 meters', '21.3400', '21.3400', 'SSTV'),
(34, '12 meters', '24.9200', '24.9250', 'RTTY'),
(35, '12 meters', '24.9250', '24.9300', 'Packet'),
(36, '10 meters', '28.0000', '28.0700', 'CW'),
(37, '10 meters', '28.0700', '28.1500', 'RTTY'),
(38, '10 meters', '28.1500', '28.1900', 'CW'),
(39, '10 meters', '28.2000', '28.3000', 'Beacons'),
(40, '10 meters', '28.3000', '29.3000', 'Phone'),
(41, '10 meters', '28.6800', '28.6800', 'SSTV'),
(42, '10 meters', '29.0000', '29.2000', 'AM'),
(43, '10 meters', '29.3000', '29.5100', 'Satellite Downlinks'),
(44, '10 meters', '29.5200', '29.5900', 'Repeater Inputs'),
(45, '10 meters', '29.6000', '29.6000', 'FM Simplex'),
(46, '10', '29.6100', '29.7000', 'Repeater Outputs'),
(47, '6 meters', '50.0000', '50.1000', 'CW, beacons'),
(48, '6 meters', '50.0600', '50.0800', 'beacon subband'),
(49, '6 meters', '50.1000', '50.3000', 'SSB, CW'),
(50, '6 meters', '50.1000', '50.1250', 'DX window'),
(51, '6 meters', '50.1250', '50.1250', 'SSB calling'),
(52, '6 meters', '50.3000', '50.6000', 'All modes'),
(53, '6 meters', '50.6000', '50.8000', 'Nonvoice communications'),
(54, '6 meters', '50.6200', '50.6200', 'Digital (packet) calling'),
(55, '6 meters', '50.8000', '51.0000', 'Radio remote control (20-kHz channels)'),
(56, '6 meters', '51.0000', '51.1000', 'Pacific DX window'),
(57, '6 meters', '51.1200', '51.4800', 'Repeater inputs (19 channels)'),
(58, '6 meters', '51.1200', '51.1800', 'Digital repeater inputs'),
(59, '6 meters', '51.5000', '51.6000', ''),
(60, '2 meters', '144.0000', '144.0500', 'EME (CW)'),
(61, '2 meters', '144.0500', '144.1000', 'General CW and weak signals'),
(62, '2 meters', '144.1000', '144.2000', 'EME and weak-signal SSB'),
(63, '2 meters', '144.2000', '144.2000', 'National calling frequency'),
(64, '2 meters', '144.2000', '144.2750', 'General SSB operation'),
(65, '2 meters', '144.2750', '144.3000', 'Propagation beacons'),
(66, '2 meters', '144.3000', '144.5000', 'New OSCAR subband'),
(67, '2 meters', '144.5000', '144.6000', 'Linear translator inputs'),
(68, '2 meters', '144.6000', '144.9000', 'FM repeater inputs'),
(69, '2 meters', '144.9000', '145.1000', 'Weak signal and FM simplex (145.01,03,05,07,09 are widely used for packet)'),
(70, '2 meters', '145.1000', '145.2000', 'Linear translator outputs'),
(71, '2 meters', '145.2000', '145.5000', 'FM repeater outputs'),
(72, '2 meters', '145.5000', '145.8000', 'Miscellaneous and experimental modes'),
(73, '2 meters', '145.8000', '146.0000', 'OSCAR subband'),
(74, '2 meters', '146.0100', '146.3700', 'Repeater inputs'),
(75, '2 meters', '146.4000', '146.5800', 'Simplex'),
(76, '2 meters', '146.5200', '146.5200', 'National Simplex Calling Frequency'),
(77, '2 meters', '146.6100', '146.9700', 'Repeater outputs'),
(78, '2 meters', '147.0000', '147.3900', 'Repeater outputs'),
(79, '2 meters', '147.4200', '147.5700', 'Simplex'),
(80, '2 meters', '147.6000', '147.9900', 'Repeater inputs'),
(81, '1.25  meters', '222.0000', '222.1500', 'Weak-signal modes'),
(82, '1.25 meters', '222.0000', '222.0250', 'EME'),
(83, '1.25 meters', '222.0500', '222.0600', 'Propagation beacons'),
(84, '1.25 meters', '222.1000', '222.1000', 'SSB & CW calling frequency'),
(85, '1.25 meters', '222.1000', '222.1500', 'Weak-signal CW & SSB'),
(86, '1.25 meters', '222.1500', '222.2500', 'Local coordinator\'s option; weak signal, ACSB, repeater inputs, control'),
(87, '1.25 meters', '222.2500', '223.3800', 'FM repeater inputs only'),
(88, '1.25 meters', '223.4000', '223.5200', 'FM simplex'),
(89, '1.25 meters', '223.5200', '223.6400', 'Digital, packet'),
(90, '1.25 meters', '223.6400', '223.7000', 'Links, control'),
(91, '1.25 meters', '223.7100', '223.8500', 'Local coordinator\'s option; FM simplex, packet, repeater outputs'),
(92, '1.25 meters', '223.8500', '224.9800', 'Repeater outputs only'),
(93, '70 centimeters', '420.0000', '426.0000', 'ATV repeater or simplex with 421.25 MHz video carrier control links and experimental'),
(94, '70 centimeters', '426.0000', '432.0000', 'ATV simplex with 427.250-MHz video carrier frequency'),
(95, '70 centimeters', '432.0000', '432.0700', 'EME (Earth-Moon-Earth)'),
(96, '70 centimeters', '432.0700', '432.1000', 'Weak-signal CW'),
(97, '70 centimeters', '432.1000', '70.0000', 'cm calling frequency'),
(98, '70 centimeters', '432.1000', '432.3000', 'Mixed-mode and weak-signal work'),
(99, '70 centimeters', '432.3000', '432.4000', 'Propagation beacons'),
(100, '70 centimeters', '432.4000', '433.0000', 'Mixed-signal work'),
(101, '70 centimeters', '433.0000', '435.0000', 'Auxiliary/repeater links'),
(102, '70 centimeters', '435.0000', '438.0000', 'Satellite only (internationally)'),
(103, '70 centimeters', '438.0000', '444.0000', 'ATV repeater input with 439.250-MHz video carrier frequency and repeater links'),
(104, '70 centimeters', '442.0000', '445.0000', 'Repeater inputs and outputs (local option)'),
(105, '70 centimeters', '445.0000', '447.0000', 'Shared by auxiliary and control links, repeaters and simplex (local option)'),
(106, '70 centimeters', '446.0000', '446.0000', 'National simplex frequency'),
(107, '70 centimeters', '447.0000', '450.0000', 'Repeater inputs and outputs (local option)');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `CAT_QSO_ERROR`
--

CREATE TABLE `CAT_QSO_ERROR` (
  `N_ID_QSO_ERROR` int(11) NOT NULL,
  `N_ID_EDITION` int(11) DEFAULT NULL,
  `S_KEY` varchar(255) DEFAULT NULL,
  `S_DESCRIPCION` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `CAT_QSO_ERROR`
--

INSERT INTO `CAT_QSO_ERROR` (`N_ID_QSO_ERROR`, `N_ID_EDITION`, `S_KEY`, `S_DESCRIPCION`) VALUES
(1, 1, 'QSO_MADE_BEFORE_CONTEST_START', 'The QSO was made before the contest start'),
(2, 1, 'QSO_MADE_AFTER_CONTEST_START', 'The QSO was made after the contest start'),
(3, 1, 'QSO_OUT_OF_BAND', 'The qso was made out of frequency on a band not allowed'),
(4, 1, 'NOT_VALID_EXCHANGE_EMMITED', 'The exchange emmited is invalid'),
(5, 1, 'QSO_ON_WARC_BAND', 'The was made on a prohibited WARC band');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `REL_CONTEO_CONTEST_LOG`
--

CREATE TABLE `REL_CONTEO_CONTEST_LOG` (
  `N_ID_REL_CONTEO_CONTEST_LOG` int(11) NOT NULL,
  `N_ID_CONTEO` int(11) NOT NULL,
  `N_ID_CONTEST_LOG` int(11) NOT NULL,
  `N_SUM_OF_POINTS` int(11) NOT NULL,
  `N_MULTIPLIERS` int(11) NOT NULL,
  `N_TOTAL_POINTS` int(11) NOT NULL,
  `IS_COMPLETE` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `REL_CONTEO_CONTEST_LOG`
--

INSERT INTO `REL_CONTEO_CONTEST_LOG` (`N_ID_REL_CONTEO_CONTEST_LOG`, `N_ID_CONTEO`, `N_ID_CONTEST_LOG`, `N_SUM_OF_POINTS`, `N_MULTIPLIERS`, `N_TOTAL_POINTS`, `IS_COMPLETE`) VALUES
(1, 1, 1, 72, 19, 1368, 0),
(2, 1, 4, 324, 67, 21708, 0),
(3, 1, 5, 102, 26, 2652, 0),
(4, 1, 7, 24, 8, 192, 1),
(5, 1, 8, 27, 9, 243, 1),
(6, 1, 9, 330, 68, 22440, 0),
(7, 1, 12, 0, 0, 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `REL_EMAIL_EMAIL_ERROR`
--

CREATE TABLE `REL_EMAIL_EMAIL_ERROR` (
  `N_ID_CAT_EMAIL_ERROR` int(11) NOT NULL,
  `N_ID_EMAIL` int(11) NOT NULL,
  `D_FECHA_REGISTRO` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `REL_EMAIL_EMAIL_ERROR`
--

INSERT INTO `REL_EMAIL_EMAIL_ERROR` (`N_ID_CAT_EMAIL_ERROR`, `N_ID_EMAIL`, `D_FECHA_REGISTRO`) VALUES
(2, 1, '2020-02-01 07:04:36'),
(2, 3, '2020-02-01 07:10:36'),
(5, 9, '2020-02-01 19:53:36'),
(5, 13, '2020-02-02 06:06:05'),
(6, 1, '2020-02-01 07:04:36'),
(6, 2, '2020-02-01 07:04:36'),
(6, 3, '2020-02-01 07:10:36'),
(6, 8, '2020-02-01 19:23:36'),
(6, 14, '2020-02-02 00:45:36'),
(7, 1, '2020-02-01 07:04:36'),
(7, 3, '2020-02-01 07:10:36'),
(7, 9, '2020-02-01 19:53:36'),
(7, 13, '2020-02-02 06:06:05');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `REL_QSO_CONTEO_QSO_ERROR`
--

CREATE TABLE `REL_QSO_CONTEO_QSO_ERROR` (
  `ID_REL_QSO_CONTEO_QSO_ERROR` int(11) NOT NULL,
  `N_ID_REL_QSO_CONTEO` int(11) DEFAULT NULL,
  `N_ID_QSO_ERROR` int(11) DEFAULT NULL,
  `D_DATETIME` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `REL_USER_ROLE`
--

CREATE TABLE `REL_USER_ROLE` (
  `N_ID_ROLE` int(11) NOT NULL,
  `N_ID_USER` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `REL_USER_ROLE`
--

INSERT INTO `REL_USER_ROLE` (`N_ID_ROLE`, `N_ID_USER`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_ATTACHED_FILE`
--

CREATE TABLE `TBL_ATTACHED_FILE` (
  `N_ID_ATTACHED_FILE` int(11) NOT NULL,
  `N_ID_EMAIL` int(11) DEFAULT NULL,
  `S_FILENAME` varchar(255) DEFAULT NULL,
  `S_CONTENT_TYPE` varchar(255) DEFAULT NULL,
  `N_LENGHT` int(11) DEFAULT NULL,
  `S_MD5_HASH` varchar(255) DEFAULT NULL,
  `S_PATH` varchar(255) DEFAULT NULL,
  `D_ISLOGFILE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_ATTACHED_FILE`
--

INSERT INTO `TBL_ATTACHED_FILE` (`N_ID_ATTACHED_FILE`, `N_ID_EMAIL`, `S_FILENAME`, `S_CONTENT_TYPE`, `N_LENGHT`, `S_MD5_HASH`, `S_PATH`, `D_ISLOGFILE`) VALUES
(1, 2, 'Sevco Finance.bmp', 'IMAGE/BMP; name=\"Sevco Finance.bmp\"', 1457442, '8fb34dd839f10b8c335591966c402013', 'contest_1/edition_1/emailcount_3680/Sevco Finance.bmp', 0),
(2, 2, 'Sevco Finance...pdf', 'APPLICATION/PDF; name=\"Sevco Finance...pdf\"', 821962, '93e2f46d8cfbabd1e2bd165019809a48', 'contest_1/edition_1/emailcount_3680/Sevco Finance...pdf', 0),
(3, 4, '9M4CPS.log', 'application/octet-stream; name=9M4CPS.log', 3254, 'e6d88cc9c819b5abc4a0d4b0f9941c3c', 'contest_1/edition_1/emailcount_3682/9M4CPS.log', 1),
(4, 5, 'PA1CW.log', 'application/octet-stream; name=PA1CW.log', 12826, '20c1e1eab62882b664f2a6313596ac4c', 'contest_1/edition_1/emailcount_3683/PA1CW.log', 1),
(5, 6, 'PA1CW.log', 'application/octet-stream; name=PA1CW.log', 12826, '20c1e1eab62882b664f2a6313596ac4c', 'contest_1/edition_1/emailcount_3684/PA1CW.log', 1),
(6, 7, 'PA1CW.log', 'application/octet-stream; name=PA1CW.log', 12826, '20c1e1eab62882b664f2a6313596ac4c', 'contest_1/edition_1/emailcount_3685/PA1CW.log', 1),
(7, 8, '=?UTF-8?B?cmQxYS5sb2c=?=', 'application/octet-stream; name=\"=?UTF-8?B?cmQxYS5sb2c=?=\"', 1474, '05b3661926798275be6c96862d18f305', 'contest_1/edition_1/emailcount_3686/=?UTF-8?B?cmQxYS5sb2c=?=', 0),
(8, 9, 'PD5PET EX RTTY 2020.cbr', 'application/octet-stream; name=\"PD5PET EX RTTY 2020.cbr\"', 4392, '46cd6f07c86399922864157a7768523f', 'contest_1/edition_1/emailcount_3687/PD5PET EX RTTY 2020.cbr', 1),
(9, 10, 'AB5XM.log', 'text/plain; charset=us-ascii', 1532, '7c88b3aae7b38230520ffdb04ad16a26', 'contest_1/edition_1/emailcount_3688/AB5XM.log', 1),
(10, 11, 'AB5XM.log', 'text/plain; charset=us-ascii', 1532, '097180b137ec0713b9ec4994f4338912', 'contest_1/edition_1/emailcount_3689/AB5XM.log', 1),
(11, 12, 'KA2AEY.log', 'text/plain; charset=UTF-8; name=KA2AEY.log', 1458, 'c5f3160e284c918845297f7e49f57bf6', 'contest_1/edition_1/emailcount_3690/KA2AEY.log', 1),
(12, 13, 'JM2LEI  XE.log', 'application/octet-stream; name=\"JM2LEI  XE.log\"', 1136, 'f72a375f9914f64da6b703ca216dab71', 'contest_1/edition_1/emailcount_3691/JM2LEI  XE.log', 1),
(13, 14, '20 Mexico RTTY.ADI', 'text/plain; charset=UTF-8; name=\"20 Mexico RTTY.ADI\"', 73982, 'ef28d1eb8e95095b29943de050b21aca', 'contest_1/edition_1/emailcount_3692/20 Mexico RTTY.ADI', 0),
(14, 15, 'K2MK.log', 'text/plain; charset=UTF-8; name=K2MK.log', 13102, '1dbf8907fd381516a0b78aecbeceefa3', 'contest_1/edition_1/emailcount_3693/K2MK.log', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_CONTEO`
--

CREATE TABLE `TBL_CONTEO` (
  `N_ID_CONTEO` int(11) NOT NULL,
  `S_DESCRIPTION` varchar(255) DEFAULT NULL,
  `S_DATETIME` datetime NOT NULL,
  `N_ID_EDITION` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_CONTEO`
--

INSERT INTO `TBL_CONTEO` (`N_ID_CONTEO`, `S_DESCRIPTION`, `S_DATETIME`, `N_ID_EDITION`) VALUES
(1, NULL, '2020-02-18 12:29:43', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_CONTEST`
--

CREATE TABLE `TBL_CONTEST` (
  `N_ID_CONTEST` int(11) NOT NULL,
  `S_DESCRIPTION` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_CONTEST`
--

INSERT INTO `TBL_CONTEST` (`N_ID_CONTEST`, `S_DESCRIPTION`) VALUES
(1, 'International RTTY Contest FMRE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_CONTEST_LOG`
--

CREATE TABLE `TBL_CONTEST_LOG` (
  `N_ID_CONTEST_LOG` int(11) NOT NULL,
  `S_ADDRESS` varchar(255) DEFAULT NULL,
  `S_ADDRESS_CITY` varchar(255) DEFAULT NULL,
  `S_ADDRESS_COUNTRY` varchar(255) DEFAULT NULL,
  `S_ADDRESS_STATE_PROVINCE` varchar(255) DEFAULT NULL,
  `S_CALLSIGN` varchar(255) DEFAULT NULL,
  `S_CATEGORY_BAND` varchar(255) DEFAULT NULL,
  `S_CATEGORY_MODE` varchar(255) DEFAULT NULL,
  `S_CATEGORY_OPERATOR` varchar(255) DEFAULT NULL,
  `S_CATEGORY_POWER` varchar(255) DEFAULT NULL,
  `D_CLAIMED_SCORE` int(11) DEFAULT NULL,
  `S_CLUB` varchar(255) DEFAULT NULL,
  `S_CONTEST` varchar(255) DEFAULT NULL,
  `S_CREATED_BY` varchar(255) DEFAULT NULL,
  `S_EMAIL_ADDRESS` varchar(255) DEFAULT NULL,
  `S_NAME` varchar(255) DEFAULT NULL,
  `S_OPERATORS` varchar(255) DEFAULT NULL,
  `S_SOAPBOX` varchar(255) DEFAULT NULL,
  `D_IS_CHECKLOG` int(11) DEFAULT NULL,
  `N_ID_EMAIL` int(11) DEFAULT NULL,
  `D_ENTITY_CODE` int(11) DEFAULT NULL,
  `D_DXCC_NOT_FOUND` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_CONTEST_LOG`
--

INSERT INTO `TBL_CONTEST_LOG` (`N_ID_CONTEST_LOG`, `S_ADDRESS`, `S_ADDRESS_CITY`, `S_ADDRESS_COUNTRY`, `S_ADDRESS_STATE_PROVINCE`, `S_CALLSIGN`, `S_CATEGORY_BAND`, `S_CATEGORY_MODE`, `S_CATEGORY_OPERATOR`, `S_CATEGORY_POWER`, `D_CLAIMED_SCORE`, `S_CLUB`, `S_CONTEST`, `S_CREATED_BY`, `S_EMAIL_ADDRESS`, `S_NAME`, `S_OPERATORS`, `S_SOAPBOX`, `D_IS_CHECKLOG`, `N_ID_EMAIL`, `D_ENTITY_CODE`, `D_DXCC_NOT_FOUND`) VALUES
(1, 'WEST MALAYSIA', NULL, NULL, NULL, '9M4CPS', NULL, NULL, NULL, NULL, 720, NULL, 'XE-RTTY', 'N1MM Logger+ 1.0.8101.0', NULL, 'PERLIS DX AMATEUR RADIO CLUB', '9M2KDX 9W2VGR', NULL, 0, 4, 299, 0),
(2, 'the Netherlands', NULL, NULL, NULL, 'PA1CW', NULL, NULL, NULL, NULL, 11235, NULL, 'XE-RTTY', 'N1MM Logger+ 1.0.8116.0', NULL, 'Theo Koning', 'PA1CW', NULL, 0, 5, NULL, NULL),
(3, 'the Netherlands', NULL, NULL, NULL, 'PA1CW', NULL, NULL, NULL, NULL, 11235, NULL, 'XE-RTTY', 'N1MM Logger+ 1.0.8116.0', NULL, 'Theo Koning', 'PA1CW', NULL, 0, 6, NULL, NULL),
(4, 'the Netherlands', NULL, NULL, NULL, 'PA1CW', NULL, NULL, NULL, NULL, 11235, NULL, 'XE-RTTY', 'N1MM Logger+ 1.0.8116.0', NULL, 'Theo Koning', 'PA1CW', NULL, 0, 7, 263, 0),
(5, 'Nederland', NULL, NULL, NULL, 'PD5PET', NULL, NULL, NULL, NULL, 0, 'VERON', 'XE RTTY 2020', 'MixW 3.2', NULL, 'Peter van der waal', 'PD5PET', NULL, 0, 9, 263, 0),
(6, 'USA', NULL, NULL, NULL, 'AB5XM', NULL, NULL, NULL, NULL, 16, NULL, 'XE-RTTY', 'N1MM Logger+ 1.0.8116.0', 'asnorthmain@yahoo.com', 'Allan Schmidt', 'AB5XM', 'Elevated 19\'Wire vertical @ 31\' with 3 counterpoise wires @ 12\'', 0, 10, NULL, NULL),
(7, 'USA', NULL, NULL, NULL, 'AB5XM', NULL, NULL, NULL, NULL, 16, NULL, 'XE-RTTY', 'N1MM Logger+ 1.0.8116.0', 'asnorthmain@yahoo.com', 'Allan Schmidt', 'AB5XM', 'Elevated 19\'Wire vertical @ 31\' with 3 counterpoise wires @ 12\'', 0, 11, 291, 0),
(8, 'UNITED STATES', NULL, NULL, NULL, 'KA2AEY', NULL, NULL, NULL, NULL, 66, NULL, 'XE-RTTY', 'N1MM Logger+ 1.0.8116.0', NULL, 'Joe Kernaghan', 'KA2AEY', NULL, 0, 12, 291, 0),
(9, 'UNITED STATES', NULL, NULL, NULL, 'K2MK', NULL, NULL, NULL, NULL, 9042, NULL, 'XE-RTTY', 'N1MM Logger+ 1.0.8116.0', NULL, 'Michael Kravitz', 'K2MK', NULL, 0, 15, 291, 0),
(12, 'Japan', NULL, NULL, NULL, 'JM2LEI', '20M', 'RTTY', 'SINGLE-OP', 'LOW', 36, NULL, 'XE-RTTY', 'CTESTWIN by JI1AQY', 'norijm2lei599@gmail.com', 'NORIYUKI NAGAHAMA', NULL, 'But bad condition.', 0, 13, 339, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_CONTEST_QSO`
--

CREATE TABLE `TBL_CONTEST_QSO` (
  `N_ID_CONTEST_QSO` int(11) NOT NULL,
  `S_CALLSIGN_EMISOR` varchar(255) DEFAULT NULL,
  `S_CALLSIGN_RECEPTOR` varchar(255) DEFAULT NULL,
  `D_DATETIME` datetime DEFAULT NULL,
  `S_EXCHANGE_EMMITED` varchar(255) DEFAULT NULL,
  `S_EXCHANGE_RECEIVED` varchar(255) DEFAULT NULL,
  `N_FREQUENCY` int(11) DEFAULT NULL,
  `S_CATEGORY_OPERATOR` varchar(255) DEFAULT NULL,
  `S_MODE` varchar(255) DEFAULT NULL,
  `S_RST_EMMITED` varchar(255) DEFAULT NULL,
  `S_RST_RECEIVED` varchar(255) DEFAULT NULL,
  `N_ID_CONTEST_LOG` int(11) DEFAULT NULL,
  `D_ENTITY_CODE` int(11) DEFAULT NULL,
  `D_DXCC_NOT_FOUND` int(11) DEFAULT NULL,
  `N_ID_FREQUENCY_BAND` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_CONTEST_QSO`
--

INSERT INTO `TBL_CONTEST_QSO` (`N_ID_CONTEST_QSO`, `S_CALLSIGN_EMISOR`, `S_CALLSIGN_RECEPTOR`, `D_DATETIME`, `S_EXCHANGE_EMMITED`, `S_EXCHANGE_RECEIVED`, `N_FREQUENCY`, `S_CATEGORY_OPERATOR`, `S_MODE`, `S_RST_EMMITED`, `S_RST_RECEIVED`, `N_ID_CONTEST_LOG`, `D_ENTITY_CODE`, `D_DXCC_NOT_FOUND`, `N_ID_FREQUENCY_BAND`) VALUES
(1, '9M4CPS', 'LY1R', '2020-02-01 12:56:00', '0001', '0009', 14096, NULL, 'RY', '599', '599', 1, 146, 0, 25),
(2, '9M4CPS', 'PA7WW', '2020-02-01 13:04:00', '0002', '0046', 14098, NULL, 'RY', '599', '599', 1, 263, 0, 25),
(3, '9M4CPS', 'UY5VA', '2020-02-01 13:07:00', '0003', '0062', 14101, NULL, 'RY', '599', '599', 1, 288, 0, 27),
(4, '9M4CPS', 'YB1TQL', '2020-02-01 13:13:00', '0004', '0014', 14087, NULL, 'RY', '599', '599', 1, 327, 0, 24),
(5, '9M4CPS', 'YB7SKM', '2020-02-01 13:22:00', '0005', '0020', 14084, NULL, 'RY', '599', '599', 1, 327, 0, 24),
(6, '9M4CPS', 'YC2YSW', '2020-02-01 13:23:00', '0006', '0008', 14084, NULL, 'RY', '599', '599', 1, 327, 0, 24),
(7, '9M4CPS', 'YB3IZK', '2020-02-01 13:26:00', '0007', '0005', 14084, NULL, 'RY', '599', '599', 1, 327, 0, 24),
(8, '9M4CPS', 'YC2DSV', '2020-02-01 13:31:00', '0008', '0008', 14084, NULL, 'RY', '599', '599', 1, 327, 0, 24),
(9, '9M4CPS', 'YB9ATS', '2020-02-01 13:40:00', '0009', '0001', 7042, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(10, '9M4CPS', 'YC2DSV', '2020-02-01 13:44:00', '0010', '0012', 7038, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(11, '9M4CPS', 'YF3FBV', '2020-02-01 13:46:00', '0011', '0007', 7041, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(12, '9M4CPS', 'YD2KJC', '2020-02-01 13:48:00', '0012', '0005', 7041, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(13, '9M4CPS', 'YC1IDC', '2020-02-01 14:16:00', '0013', '0006', 7049, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(14, '9M4CPS', 'YB3IZK', '2020-02-01 14:18:00', '0014', '0009', 7049, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(15, '9M4CPS', 'YC2YSW', '2020-02-01 14:19:00', '0015', '0015', 7049, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(16, '9M4CPS', 'DU1JM', '2020-02-01 14:24:00', '0016', '0017', 14084, NULL, 'RY', '599', '599', 1, 375, 0, 24),
(17, '9M4CPS', 'YB1TQL', '2020-02-01 14:37:00', '0017', '0031', 7043, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(18, '9M4CPS', '9W6AJA', '2020-02-01 14:56:00', '0018', '0002', 7051, NULL, 'RY', '599', '599', 1, 46, 0, NULL),
(19, '9M4CPS', 'YL5T', '2020-02-01 14:58:00', '0019', '0079', 7058, NULL, 'RY', '599', '599', 1, 145, 0, NULL),
(20, '9M4CPS', 'DU1JM', '2020-02-01 15:06:00', '0020', '0025', 7043, NULL, 'RY', '599', '599', 1, 375, 0, NULL),
(21, '9M4CPS', 'YC3ATK', '2020-02-01 15:10:00', '0021', '0017', 7043, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(22, '9M4CPS', 'YC1RKT', '2020-02-01 15:12:00', '0022', '0011', 7043, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(23, '9M4CPS', 'YB7OO', '2020-02-01 15:47:00', '0023', '0001', 7050, NULL, 'RY', '599', '599', 1, 327, 0, NULL),
(24, '9M4CPS', 'YO9BCM', '2020-02-01 15:55:00', '0024', '0088', 7047, NULL, 'RY', '599', '599', 1, 275, 0, NULL),
(25, 'PA1CW', 'UT7FA', '2020-02-01 12:21:00', '0001', '0012', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(26, 'PA1CW', 'EU1DX', '2020-02-01 12:23:00', '0002', '0002', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(27, 'PA1CW', 'UR5IHD', '2020-02-01 12:24:00', '0003', '0006', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(28, 'PA1CW', 'UT2EF', '2020-02-01 12:25:00', '0004', '0016', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(29, 'PA1CW', 'UR4EI', '2020-02-01 12:25:00', '0005', '0009', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(30, 'PA1CW', 'YO6HSU', '2020-02-01 12:26:00', '0006', '0015', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(31, 'PA1CW', 'RN1AO', '2020-02-01 12:27:00', '0007', '0013', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(32, 'PA1CW', 'RG3T', '2020-02-01 12:28:00', '0008', '0003', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(33, 'PA1CW', 'R6DM', '2020-02-01 12:28:00', '0009', '0010', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(34, 'PA1CW', 'UA3XO', '2020-02-01 12:30:00', '0010', '0015', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(35, 'PA1CW', 'RZ3ZZ', '2020-02-01 12:31:00', '0011', '0011', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(36, 'PA1CW', 'UR4CU', '2020-02-01 12:32:00', '0012', '0009', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(37, 'PA1CW', 'US0ZK', '2020-02-01 12:33:00', '0013', '0005', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(38, 'PA1CW', 'UX8ZA', '2020-02-01 12:34:00', '0014', '0006', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(39, 'PA1CW', 'RA4HL', '2020-02-01 12:35:00', '0015', '0024', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(40, 'PA1CW', 'YO9BCM', '2020-02-01 12:36:00', '0016', '0016', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(41, 'PA1CW', 'RA3LJ', '2020-02-01 12:36:00', '0017', '0013', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(42, 'PA1CW', 'R7LY', '2020-02-01 12:38:00', '0018', '0017', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(43, 'PA1CW', 'EW8G', '2020-02-01 12:39:00', '0019', '0011', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(44, 'PA1CW', 'OH2LNH', '2020-02-01 12:39:00', '0020', '0021', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(45, 'PA1CW', 'YO4AAC', '2020-02-01 12:43:00', '0021', '0003', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(46, 'PA1CW', 'RT6DI', '2020-02-01 13:22:00', '0022', '0038', 14092, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(47, 'PA1CW', 'RT3P', '2020-02-01 13:25:00', '0023', '0006', 14081, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(48, 'PA1CW', 'ER1PB', '2020-02-01 13:25:00', '0024', '0043', 14083, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(49, 'PA1CW', 'YO9BPX', '2020-02-01 13:28:00', '0025', '0044', 14084, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(50, 'PA1CW', 'LZ5K', '2020-02-01 13:28:00', '0026', '0052', 14083, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(51, 'PA1CW', 'SV1JFL', '2020-02-01 13:30:00', '0027', '0032', 14084, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(52, 'PA1CW', 'W3FIZ', '2020-02-01 13:36:00', '0028', '0079', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(53, 'PA1CW', 'OH2HAN', '2020-02-01 13:41:00', '0029', '0103', 14090, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(54, 'PA1CW', 'UT5EPP', '2020-02-01 13:42:00', '0030', '0071', 14093, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(55, 'PA1CW', 'UX7QV', '2020-02-01 13:44:00', '0031', '0067', 14094, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(56, 'PA1CW', 'EW6DM', '2020-02-01 13:45:00', '0032', '0062', 14100, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(57, 'PA1CW', 'PD2JAM', '2020-02-01 13:49:00', '0033', '0050', 14090, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(58, 'PA1CW', 'K4FX', '2020-02-01 13:51:00', '0034', '0044', 14089, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(59, 'PA1CW', 'UA6CE', '2020-02-01 13:53:00', '0035', '0084', 14087, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(60, 'PA1CW', 'OZ4NE', '2020-02-01 13:55:00', '0036', '0021', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(61, 'PA1CW', 'SO50WD', '2020-02-01 13:56:00', '0037', '0015', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(62, 'PA1CW', 'OH5UQ', '2020-02-01 13:56:00', '0038', '0041', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(63, 'PA1CW', 'LY2SA', '2020-02-01 13:57:00', '0039', '0006', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(64, 'PA1CW', 'UR4MVK', '2020-02-01 13:58:00', '0040', '0017', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(65, 'PA1CW', 'DL6NWA', '2020-02-01 14:00:00', '0041', '0001', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(66, 'PA1CW', 'R4OF', '2020-02-01 14:01:00', '0042', '0038', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(67, 'PA1CW', 'UA6Y', '2020-02-01 14:02:00', '0043', '0075', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(68, 'PA1CW', 'DL5KUD', '2020-02-01 14:02:00', '0044', '0018', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(69, 'PA1CW', 'IK3ITM', '2020-02-01 14:07:00', '0045', '0001', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(70, 'PA1CW', 'OZ1AOO', '2020-02-01 14:08:00', '0046', '0036', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(71, 'PA1CW', 'DL5ARM', '2020-02-01 14:09:00', '0047', '0018', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(72, 'PA1CW', 'OK2EA', '2020-02-01 14:09:00', '0048', '0037', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(73, 'PA1CW', 'DL4ME', '2020-02-01 14:10:00', '0049', '0003', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(74, 'PA1CW', 'SP4BPH', '2020-02-01 14:12:00', '0050', '0024', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(75, 'PA1CW', 'EW8G', '2020-02-01 14:13:00', '0051', '0051', 7041, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(76, 'PA1CW', 'UA4SJO', '2020-02-01 14:16:00', '0052', '0030', 7052, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(77, 'PA1CW', 'UC4I', '2020-02-01 14:19:00', '0053', '0044', 7052, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(78, 'PA1CW', 'OH2HAN', '2020-02-01 14:20:00', '0054', '0137', 7050, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(79, 'PA1CW', 'OH3FM', '2020-02-01 14:22:00', '0055', '0126', 7046, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(80, 'PA1CW', 'DJ3IW', '2020-02-01 14:23:00', '0056', '0043', 7046, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(81, 'PA1CW', 'YO2IS', '2020-02-01 14:29:00', '0057', '0040', 7048, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(82, 'PA1CW', 'IK7RVY', '2020-02-01 14:57:00', '0059', '0104', 14089, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(83, 'PA1CW', 'YQ6A', '2020-02-01 15:01:00', '0060', '0051', 14090, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(84, 'PA1CW', 'K9OM', '2020-02-01 15:06:00', '0061', '0057', 14100, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(85, 'PA1CW', 'N7WY', '2020-02-01 15:07:00', '0062', '0110', 14099, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(86, 'PA1CW', 'PA5WT', '2020-02-01 15:08:00', '0063', '0062', 14097, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(87, 'PA1CW', 'EB5F', '2020-02-01 15:10:00', '0064', '0004', 14092, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(88, 'PA1CW', 'K1VW', '2020-02-01 15:10:00', '0065', '0007', 14092, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(89, 'PA1CW', 'WB0WIV', '2020-02-01 15:12:00', '0066', '0025', 14092, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(90, 'PA1CW', 'RG3T', '2020-02-01 15:15:00', '0067', '0014', 7049, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(91, 'PA1CW', '2E0CVN', '2020-02-01 15:17:00', '0068', '0026', 7049, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(92, 'PA1CW', 'OH2EUU', '2020-02-01 15:18:00', '0069', '0098', 7049, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(93, 'PA1CW', 'R6LAQ', '2020-02-01 15:19:00', '0070', '0075', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(94, 'PA1CW', 'LA/DL4HCF', '2020-02-01 15:21:00', '0071', '0003', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(95, 'PA1CW', 'I2XLF', '2020-02-01 15:22:00', '0072', '0048', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(96, 'PA1CW', 'IZ4UEZ', '2020-02-01 15:22:00', '0073', '0008', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(97, 'PA1CW', 'UR4EI', '2020-02-01 15:23:00', '0074', '0064', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(98, 'PA1CW', 'LY4K', '2020-02-01 15:23:00', '0075', '0048', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(99, 'PA1CW', 'OH7L', '2020-02-01 15:24:00', '0076', '0094', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(100, 'PA1CW', 'R7RIB', '2020-02-01 15:26:00', '0077', '0030', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(101, 'PA1CW', 'F4FRC', '2020-02-01 15:27:00', '0078', '0006', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(102, 'PA1CW', 'SP6EIY', '2020-02-01 15:28:00', '0079', '0057', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(103, 'PA1CW', 'UT5EPP', '2020-02-01 15:29:00', '0080', '0133', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(104, 'PA1CW', 'UT7FA', '2020-02-01 15:30:00', '0081', '0098', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(105, 'PA1CW', 'R7LY', '2020-02-01 15:31:00', '0082', '0051', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(106, 'PA1CW', 'SE4E', '2020-02-01 15:33:00', '0083', '0119', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(107, 'PA1CW', 'RU2F', '2020-02-01 15:34:00', '0084', '0008', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(108, 'PA1CW', 'UZ1WW', '2020-02-01 15:34:00', '0085', '0080', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(109, 'PA1CW', 'RU6YJ', '2020-02-01 15:36:00', '0086', '0065', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(110, 'PA1CW', 'EU1DX', '2020-02-01 15:37:00', '0088', '0057', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(111, 'PA1CW', 'EA1X', '2020-02-01 15:38:00', '0089', '0067', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(112, 'PA1CW', 'I2WIJ', '2020-02-01 15:41:00', '0090', '0077', 7051, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(113, 'PA1CW', 'SV9MBH', '2020-02-01 15:43:00', '0091', '0027', 7042, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(114, 'PA1CW', 'ER1PB', '2020-02-01 15:45:00', '0092', '0103', 7044, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(115, 'PA1CW', 'R1AV', '2020-02-01 15:46:00', '0093', '0048', 7045, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(116, 'PA1CW', 'RT9S', '2020-02-01 15:48:00', '0094', '0094', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(117, 'PA1CW', 'SM5IMO', '2020-02-01 15:52:00', '0095', '0122', 7055, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(118, 'PA1CW', 'S58Q', '2020-02-01 15:54:00', '0096', '0045', 7053, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(119, 'PA1CW', 'OH2LNH', '2020-02-01 15:55:00', '0097', '0051', 7056, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(120, 'PA1CW', 'F4FDR', '2020-02-01 15:58:00', '0098', '0108', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(121, 'PA1CW', 'OM3RM', '2020-02-01 16:01:00', '0099', '0155', 7043, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(122, 'PA1CW', 'RN1AO', '2020-02-01 16:09:00', '0100', '0030', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(123, 'PA1CW', 'LY2CG', '2020-02-01 16:10:00', '0101', '0047', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(124, 'PA1CW', 'LY2PAD', '2020-02-01 16:11:00', '0102', '0058', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(125, 'PA1CW', 'R7MT', '2020-02-01 16:12:00', '0103', '0076', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(126, 'PA1CW', 'RU5X', '2020-02-01 16:13:00', '0104', '0048', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(127, 'PA1CW', 'PA5WT', '2020-02-01 16:14:00', '0105', '0090', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(128, 'PA1CW', 'R2XM', '2020-02-01 16:17:00', '0106', '0054', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(129, 'PA1CW', 'IW1CBG', '2020-02-01 16:17:00', '0107', '0028', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(130, 'PA1CW', 'RN2FQ', '2020-02-01 16:19:00', '0108', '0007', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(131, 'PA1CW', 'EW8OM', '2020-02-01 16:20:00', '0109', '0012', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(132, 'PA1CW', 'RA1ALC', '2020-02-01 16:21:00', '0110', '0069', 7047, NULL, 'RY', '599', '599', 2, NULL, NULL, NULL),
(133, 'PA1CW', 'UT7FA', '2020-02-01 12:21:00', '0001', '0012', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(134, 'PA1CW', 'EU1DX', '2020-02-01 12:23:00', '0002', '0002', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(135, 'PA1CW', 'UR5IHD', '2020-02-01 12:24:00', '0003', '0006', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(136, 'PA1CW', 'UT2EF', '2020-02-01 12:25:00', '0004', '0016', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(137, 'PA1CW', 'UR4EI', '2020-02-01 12:25:00', '0005', '0009', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(138, 'PA1CW', 'YO6HSU', '2020-02-01 12:26:00', '0006', '0015', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(139, 'PA1CW', 'RN1AO', '2020-02-01 12:27:00', '0007', '0013', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(140, 'PA1CW', 'RG3T', '2020-02-01 12:28:00', '0008', '0003', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(141, 'PA1CW', 'R6DM', '2020-02-01 12:28:00', '0009', '0010', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(142, 'PA1CW', 'UA3XO', '2020-02-01 12:30:00', '0010', '0015', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(143, 'PA1CW', 'RZ3ZZ', '2020-02-01 12:31:00', '0011', '0011', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(144, 'PA1CW', 'UR4CU', '2020-02-01 12:32:00', '0012', '0009', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(145, 'PA1CW', 'US0ZK', '2020-02-01 12:33:00', '0013', '0005', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(146, 'PA1CW', 'UX8ZA', '2020-02-01 12:34:00', '0014', '0006', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(147, 'PA1CW', 'RA4HL', '2020-02-01 12:35:00', '0015', '0024', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(148, 'PA1CW', 'YO9BCM', '2020-02-01 12:36:00', '0016', '0016', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(149, 'PA1CW', 'RA3LJ', '2020-02-01 12:36:00', '0017', '0013', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(150, 'PA1CW', 'R7LY', '2020-02-01 12:38:00', '0018', '0017', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(151, 'PA1CW', 'EW8G', '2020-02-01 12:39:00', '0019', '0011', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(152, 'PA1CW', 'OH2LNH', '2020-02-01 12:39:00', '0020', '0021', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(153, 'PA1CW', 'YO4AAC', '2020-02-01 12:43:00', '0021', '0003', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(154, 'PA1CW', 'RT6DI', '2020-02-01 13:22:00', '0022', '0038', 14092, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(155, 'PA1CW', 'RT3P', '2020-02-01 13:25:00', '0023', '0006', 14081, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(156, 'PA1CW', 'ER1PB', '2020-02-01 13:25:00', '0024', '0043', 14083, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(157, 'PA1CW', 'YO9BPX', '2020-02-01 13:28:00', '0025', '0044', 14084, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(158, 'PA1CW', 'LZ5K', '2020-02-01 13:28:00', '0026', '0052', 14083, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(159, 'PA1CW', 'SV1JFL', '2020-02-01 13:30:00', '0027', '0032', 14084, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(160, 'PA1CW', 'W3FIZ', '2020-02-01 13:36:00', '0028', '0079', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(161, 'PA1CW', 'OH2HAN', '2020-02-01 13:41:00', '0029', '0103', 14090, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(162, 'PA1CW', 'UT5EPP', '2020-02-01 13:42:00', '0030', '0071', 14093, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(163, 'PA1CW', 'UX7QV', '2020-02-01 13:44:00', '0031', '0067', 14094, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(164, 'PA1CW', 'EW6DM', '2020-02-01 13:45:00', '0032', '0062', 14100, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(165, 'PA1CW', 'PD2JAM', '2020-02-01 13:49:00', '0033', '0050', 14090, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(166, 'PA1CW', 'K4FX', '2020-02-01 13:51:00', '0034', '0044', 14089, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(167, 'PA1CW', 'UA6CE', '2020-02-01 13:53:00', '0035', '0084', 14087, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(168, 'PA1CW', 'OZ4NE', '2020-02-01 13:55:00', '0036', '0021', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(169, 'PA1CW', 'SO50WD', '2020-02-01 13:56:00', '0037', '0015', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(170, 'PA1CW', 'OH5UQ', '2020-02-01 13:56:00', '0038', '0041', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(171, 'PA1CW', 'LY2SA', '2020-02-01 13:57:00', '0039', '0006', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(172, 'PA1CW', 'UR4MVK', '2020-02-01 13:58:00', '0040', '0017', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(173, 'PA1CW', 'DL6NWA', '2020-02-01 14:00:00', '0041', '0001', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(174, 'PA1CW', 'R4OF', '2020-02-01 14:01:00', '0042', '0038', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(175, 'PA1CW', 'UA6Y', '2020-02-01 14:02:00', '0043', '0075', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(176, 'PA1CW', 'DL5KUD', '2020-02-01 14:02:00', '0044', '0018', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(177, 'PA1CW', 'IK3ITM', '2020-02-01 14:07:00', '0045', '0001', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(178, 'PA1CW', 'OZ1AOO', '2020-02-01 14:08:00', '0046', '0036', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(179, 'PA1CW', 'DL5ARM', '2020-02-01 14:09:00', '0047', '0018', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(180, 'PA1CW', 'OK2EA', '2020-02-01 14:09:00', '0048', '0037', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(181, 'PA1CW', 'DL4ME', '2020-02-01 14:10:00', '0049', '0003', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(182, 'PA1CW', 'SP4BPH', '2020-02-01 14:12:00', '0050', '0024', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(183, 'PA1CW', 'EW8G', '2020-02-01 14:13:00', '0051', '0051', 7041, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(184, 'PA1CW', 'UA4SJO', '2020-02-01 14:16:00', '0052', '0030', 7052, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(185, 'PA1CW', 'UC4I', '2020-02-01 14:19:00', '0053', '0044', 7052, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(186, 'PA1CW', 'OH2HAN', '2020-02-01 14:20:00', '0054', '0137', 7050, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(187, 'PA1CW', 'OH3FM', '2020-02-01 14:22:00', '0055', '0126', 7046, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(188, 'PA1CW', 'DJ3IW', '2020-02-01 14:23:00', '0056', '0043', 7046, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(189, 'PA1CW', 'YO2IS', '2020-02-01 14:29:00', '0057', '0040', 7048, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(190, 'PA1CW', 'IK7RVY', '2020-02-01 14:57:00', '0059', '0104', 14089, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(191, 'PA1CW', 'YQ6A', '2020-02-01 15:01:00', '0060', '0051', 14090, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(192, 'PA1CW', 'K9OM', '2020-02-01 15:06:00', '0061', '0057', 14100, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(193, 'PA1CW', 'N7WY', '2020-02-01 15:07:00', '0062', '0110', 14099, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(194, 'PA1CW', 'PA5WT', '2020-02-01 15:08:00', '0063', '0062', 14097, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(195, 'PA1CW', 'EB5F', '2020-02-01 15:10:00', '0064', '0004', 14092, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(196, 'PA1CW', 'K1VW', '2020-02-01 15:10:00', '0065', '0007', 14092, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(197, 'PA1CW', 'WB0WIV', '2020-02-01 15:12:00', '0066', '0025', 14092, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(198, 'PA1CW', 'RG3T', '2020-02-01 15:15:00', '0067', '0014', 7049, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(199, 'PA1CW', '2E0CVN', '2020-02-01 15:17:00', '0068', '0026', 7049, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(200, 'PA1CW', 'OH2EUU', '2020-02-01 15:18:00', '0069', '0098', 7049, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(201, 'PA1CW', 'R6LAQ', '2020-02-01 15:19:00', '0070', '0075', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(202, 'PA1CW', 'LA/DL4HCF', '2020-02-01 15:21:00', '0071', '0003', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(203, 'PA1CW', 'I2XLF', '2020-02-01 15:22:00', '0072', '0048', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(204, 'PA1CW', 'IZ4UEZ', '2020-02-01 15:22:00', '0073', '0008', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(205, 'PA1CW', 'UR4EI', '2020-02-01 15:23:00', '0074', '0064', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(206, 'PA1CW', 'LY4K', '2020-02-01 15:23:00', '0075', '0048', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(207, 'PA1CW', 'OH7L', '2020-02-01 15:24:00', '0076', '0094', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(208, 'PA1CW', 'R7RIB', '2020-02-01 15:26:00', '0077', '0030', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(209, 'PA1CW', 'F4FRC', '2020-02-01 15:27:00', '0078', '0006', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(210, 'PA1CW', 'SP6EIY', '2020-02-01 15:28:00', '0079', '0057', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(211, 'PA1CW', 'UT5EPP', '2020-02-01 15:29:00', '0080', '0133', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(212, 'PA1CW', 'UT7FA', '2020-02-01 15:30:00', '0081', '0098', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(213, 'PA1CW', 'R7LY', '2020-02-01 15:31:00', '0082', '0051', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(214, 'PA1CW', 'SE4E', '2020-02-01 15:33:00', '0083', '0119', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(215, 'PA1CW', 'RU2F', '2020-02-01 15:34:00', '0084', '0008', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(216, 'PA1CW', 'UZ1WW', '2020-02-01 15:34:00', '0085', '0080', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(217, 'PA1CW', 'RU6YJ', '2020-02-01 15:36:00', '0086', '0065', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(218, 'PA1CW', 'EU1DX', '2020-02-01 15:37:00', '0088', '0057', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(219, 'PA1CW', 'EA1X', '2020-02-01 15:38:00', '0089', '0067', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(220, 'PA1CW', 'I2WIJ', '2020-02-01 15:41:00', '0090', '0077', 7051, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(221, 'PA1CW', 'SV9MBH', '2020-02-01 15:43:00', '0091', '0027', 7042, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(222, 'PA1CW', 'ER1PB', '2020-02-01 15:45:00', '0092', '0103', 7044, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(223, 'PA1CW', 'R1AV', '2020-02-01 15:46:00', '0093', '0048', 7045, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(224, 'PA1CW', 'RT9S', '2020-02-01 15:48:00', '0094', '0094', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(225, 'PA1CW', 'SM5IMO', '2020-02-01 15:52:00', '0095', '0122', 7055, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(226, 'PA1CW', 'S58Q', '2020-02-01 15:54:00', '0096', '0045', 7053, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(227, 'PA1CW', 'OH2LNH', '2020-02-01 15:55:00', '0097', '0051', 7056, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(228, 'PA1CW', 'F4FDR', '2020-02-01 15:58:00', '0098', '0108', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(229, 'PA1CW', 'OM3RM', '2020-02-01 16:01:00', '0099', '0155', 7043, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(230, 'PA1CW', 'RN1AO', '2020-02-01 16:09:00', '0100', '0030', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(231, 'PA1CW', 'LY2CG', '2020-02-01 16:10:00', '0101', '0047', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(232, 'PA1CW', 'LY2PAD', '2020-02-01 16:11:00', '0102', '0058', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(233, 'PA1CW', 'R7MT', '2020-02-01 16:12:00', '0103', '0076', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(234, 'PA1CW', 'RU5X', '2020-02-01 16:13:00', '0104', '0048', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(235, 'PA1CW', 'PA5WT', '2020-02-01 16:14:00', '0105', '0090', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(236, 'PA1CW', 'R2XM', '2020-02-01 16:17:00', '0106', '0054', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(237, 'PA1CW', 'IW1CBG', '2020-02-01 16:17:00', '0107', '0028', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(238, 'PA1CW', 'RN2FQ', '2020-02-01 16:19:00', '0108', '0007', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(239, 'PA1CW', 'EW8OM', '2020-02-01 16:20:00', '0109', '0012', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(240, 'PA1CW', 'RA1ALC', '2020-02-01 16:21:00', '0110', '0069', 7047, NULL, 'RY', '599', '599', 3, NULL, NULL, NULL),
(241, 'PA1CW', 'UT7FA', '2020-02-01 12:21:00', '0001', '0012', 14087, NULL, 'RY', '599', '599', 4, 288, 0, 24),
(242, 'PA1CW', 'EU1DX', '2020-02-01 12:23:00', '0002', '0002', 14087, NULL, 'RY', '599', '599', 4, 27, 0, 24),
(243, 'PA1CW', 'UR5IHD', '2020-02-01 12:24:00', '0003', '0006', 14087, NULL, 'RY', '599', '599', 4, 288, 0, 24),
(244, 'PA1CW', 'UT2EF', '2020-02-01 12:25:00', '0004', '0016', 14087, NULL, 'RY', '599', '599', 4, 288, 0, 24),
(245, 'PA1CW', 'UR4EI', '2020-02-01 12:25:00', '0005', '0009', 14087, NULL, 'RY', '599', '599', 4, 288, 0, 24),
(246, 'PA1CW', 'YO6HSU', '2020-02-01 12:26:00', '0006', '0015', 14087, NULL, 'RY', '599', '599', 4, 275, 0, 24),
(247, 'PA1CW', 'RN1AO', '2020-02-01 12:27:00', '0007', '0013', 14087, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(248, 'PA1CW', 'RG3T', '2020-02-01 12:28:00', '0008', '0003', 14087, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(249, 'PA1CW', 'R6DM', '2020-02-01 12:28:00', '0009', '0010', 14087, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(250, 'PA1CW', 'UA3XO', '2020-02-01 12:30:00', '0010', '0015', 14087, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(251, 'PA1CW', 'RZ3ZZ', '2020-02-01 12:31:00', '0011', '0011', 14087, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(252, 'PA1CW', 'UR4CU', '2020-02-01 12:32:00', '0012', '0009', 14087, NULL, 'RY', '599', '599', 4, 288, 0, 24),
(253, 'PA1CW', 'US0ZK', '2020-02-01 12:33:00', '0013', '0005', 14087, NULL, 'RY', '599', '599', 4, 288, 0, 24),
(254, 'PA1CW', 'UX8ZA', '2020-02-01 12:34:00', '0014', '0006', 14087, NULL, 'RY', '599', '599', 4, 288, 0, 24),
(255, 'PA1CW', 'RA4HL', '2020-02-01 12:35:00', '0015', '0024', 14087, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(256, 'PA1CW', 'YO9BCM', '2020-02-01 12:36:00', '0016', '0016', 14087, NULL, 'RY', '599', '599', 4, 275, 0, 24),
(257, 'PA1CW', 'RA3LJ', '2020-02-01 12:36:00', '0017', '0013', 14087, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(258, 'PA1CW', 'R7LY', '2020-02-01 12:38:00', '0018', '0017', 14087, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(259, 'PA1CW', 'EW8G', '2020-02-01 12:39:00', '0019', '0011', 14087, NULL, 'RY', '599', '599', 4, 27, 0, 24),
(260, 'PA1CW', 'OH2LNH', '2020-02-01 12:39:00', '0020', '0021', 14087, NULL, 'RY', '599', '599', 4, 224, 0, 24),
(261, 'PA1CW', 'YO4AAC', '2020-02-01 12:43:00', '0021', '0003', 14087, NULL, 'RY', '599', '599', 4, 275, 0, 24),
(262, 'PA1CW', 'RT6DI', '2020-02-01 13:22:00', '0022', '0038', 14092, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(263, 'PA1CW', 'RT3P', '2020-02-01 13:25:00', '0023', '0006', 14081, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(264, 'PA1CW', 'ER1PB', '2020-02-01 13:25:00', '0024', '0043', 14083, NULL, 'RY', '599', '599', 4, 179, 0, 24),
(265, 'PA1CW', 'YO9BPX', '2020-02-01 13:28:00', '0025', '0044', 14084, NULL, 'RY', '599', '599', 4, 275, 0, 24),
(266, 'PA1CW', 'LZ5K', '2020-02-01 13:28:00', '0026', '0052', 14083, NULL, 'RY', '599', '599', 4, 212, 0, 24),
(267, 'PA1CW', 'SV1JFL', '2020-02-01 13:30:00', '0027', '0032', 14084, NULL, 'RY', '599', '599', 4, 236, 0, 24),
(268, 'PA1CW', 'W3FIZ', '2020-02-01 13:36:00', '0028', '0079', 14087, NULL, 'RY', '599', '599', 4, 291, 0, 24),
(269, 'PA1CW', 'OH2HAN', '2020-02-01 13:41:00', '0029', '0103', 14090, NULL, 'RY', '599', '599', 4, 224, 0, 24),
(270, 'PA1CW', 'UT5EPP', '2020-02-01 13:42:00', '0030', '0071', 14093, NULL, 'RY', '599', '599', 4, 288, 0, 24),
(271, 'PA1CW', 'UX7QV', '2020-02-01 13:44:00', '0031', '0067', 14094, NULL, 'RY', '599', '599', 4, 288, 0, 24),
(272, 'PA1CW', 'EW6DM', '2020-02-01 13:45:00', '0032', '0062', 14100, NULL, 'RY', '599', '599', 4, 27, 0, 26),
(273, 'PA1CW', 'PD2JAM', '2020-02-01 13:49:00', '0033', '0050', 14090, NULL, 'RY', '599', '599', 4, 263, 0, 24),
(274, 'PA1CW', 'K4FX', '2020-02-01 13:51:00', '0034', '0044', 14089, NULL, 'RY', '599', '599', 4, 291, 0, 24),
(275, 'PA1CW', 'UA6CE', '2020-02-01 13:53:00', '0035', '0084', 14087, NULL, 'RY', '599', '599', 4, 54, 0, 24),
(276, 'PA1CW', 'OZ4NE', '2020-02-01 13:55:00', '0036', '0021', 7041, NULL, 'RY', '599', '599', 4, 221, 0, NULL),
(277, 'PA1CW', 'SO50WD', '2020-02-01 13:56:00', '0037', '0015', 7041, NULL, 'RY', '599', '599', 4, 269, 0, NULL),
(278, 'PA1CW', 'OH5UQ', '2020-02-01 13:56:00', '0038', '0041', 7041, NULL, 'RY', '599', '599', 4, 224, 0, NULL),
(279, 'PA1CW', 'LY2SA', '2020-02-01 13:57:00', '0039', '0006', 7041, NULL, 'RY', '599', '599', 4, 146, 0, NULL),
(280, 'PA1CW', 'UR4MVK', '2020-02-01 13:58:00', '0040', '0017', 7041, NULL, 'RY', '599', '599', 4, 288, 0, NULL),
(281, 'PA1CW', 'DL6NWA', '2020-02-01 14:00:00', '0041', '0001', 7041, NULL, 'RY', '599', '599', 4, 230, 0, NULL),
(282, 'PA1CW', 'R4OF', '2020-02-01 14:01:00', '0042', '0038', 7041, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(283, 'PA1CW', 'UA6Y', '2020-02-01 14:02:00', '0043', '0075', 7041, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(284, 'PA1CW', 'DL5KUD', '2020-02-01 14:02:00', '0044', '0018', 7041, NULL, 'RY', '599', '599', 4, 230, 0, NULL),
(285, 'PA1CW', 'IK3ITM', '2020-02-01 14:07:00', '0045', '0001', 7041, NULL, 'RY', '599', '599', 4, 248, 0, NULL),
(286, 'PA1CW', 'OZ1AOO', '2020-02-01 14:08:00', '0046', '0036', 7041, NULL, 'RY', '599', '599', 4, 221, 0, NULL),
(287, 'PA1CW', 'DL5ARM', '2020-02-01 14:09:00', '0047', '0018', 7041, NULL, 'RY', '599', '599', 4, 230, 0, NULL),
(288, 'PA1CW', 'OK2EA', '2020-02-01 14:09:00', '0048', '0037', 7041, NULL, 'RY', '599', '599', 4, 503, 0, NULL),
(289, 'PA1CW', 'DL4ME', '2020-02-01 14:10:00', '0049', '0003', 7041, NULL, 'RY', '599', '599', 4, 230, 0, NULL),
(290, 'PA1CW', 'SP4BPH', '2020-02-01 14:12:00', '0050', '0024', 7041, NULL, 'RY', '599', '599', 4, 269, 0, NULL),
(291, 'PA1CW', 'EW8G', '2020-02-01 14:13:00', '0051', '0051', 7041, NULL, 'RY', '599', '599', 4, 27, 0, NULL),
(292, 'PA1CW', 'UA4SJO', '2020-02-01 14:16:00', '0052', '0030', 7052, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(293, 'PA1CW', 'UC4I', '2020-02-01 14:19:00', '0053', '0044', 7052, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(294, 'PA1CW', 'OH2HAN', '2020-02-01 14:20:00', '0054', '0137', 7050, NULL, 'RY', '599', '599', 4, 224, 0, NULL),
(295, 'PA1CW', 'OH3FM', '2020-02-01 14:22:00', '0055', '0126', 7046, NULL, 'RY', '599', '599', 4, 224, 0, NULL),
(296, 'PA1CW', 'DJ3IW', '2020-02-01 14:23:00', '0056', '0043', 7046, NULL, 'RY', '599', '599', 4, 230, 0, NULL),
(297, 'PA1CW', 'YO2IS', '2020-02-01 14:29:00', '0057', '0040', 7048, NULL, 'RY', '599', '599', 4, 275, 0, NULL),
(298, 'PA1CW', 'IK7RVY', '2020-02-01 14:57:00', '0059', '0104', 14089, NULL, 'RY', '599', '599', 4, 248, 0, 24),
(299, 'PA1CW', 'YQ6A', '2020-02-01 15:01:00', '0060', '0051', 14090, NULL, 'RY', '599', '599', 4, 275, 0, 24),
(300, 'PA1CW', 'K9OM', '2020-02-01 15:06:00', '0061', '0057', 14100, NULL, 'RY', '599', '599', 4, 291, 0, 26),
(301, 'PA1CW', 'N7WY', '2020-02-01 15:07:00', '0062', '0110', 14099, NULL, 'RY', '599', '599', 4, 291, 0, 25),
(302, 'PA1CW', 'PA5WT', '2020-02-01 15:08:00', '0063', '0062', 14097, NULL, 'RY', '599', '599', 4, 263, 0, 25),
(303, 'PA1CW', 'EB5F', '2020-02-01 15:10:00', '0064', '0004', 14092, NULL, 'RY', '599', '599', 4, 281, 0, 24),
(304, 'PA1CW', 'K1VW', '2020-02-01 15:10:00', '0065', '0007', 14092, NULL, 'RY', '599', '599', 4, 291, 0, 24),
(305, 'PA1CW', 'WB0WIV', '2020-02-01 15:12:00', '0066', '0025', 14092, NULL, 'RY', '599', '599', 4, 291, 0, 24),
(306, 'PA1CW', 'RG3T', '2020-02-01 15:15:00', '0067', '0014', 7049, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(307, 'PA1CW', '2E0CVN', '2020-02-01 15:17:00', '0068', '0026', 7049, NULL, 'RY', '599', '599', 4, 223, 0, NULL),
(308, 'PA1CW', 'OH2EUU', '2020-02-01 15:18:00', '0069', '0098', 7049, NULL, 'RY', '599', '599', 4, 224, 0, NULL),
(309, 'PA1CW', 'R6LAQ', '2020-02-01 15:19:00', '0070', '0075', 7053, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(310, 'PA1CW', 'LA/DL4HCF', '2020-02-01 15:21:00', '0071', '0003', 7053, NULL, 'RY', '599', '599', 4, 230, 0, NULL),
(311, 'PA1CW', 'I2XLF', '2020-02-01 15:22:00', '0072', '0048', 7053, NULL, 'RY', '599', '599', 4, 248, 0, NULL),
(312, 'PA1CW', 'IZ4UEZ', '2020-02-01 15:22:00', '0073', '0008', 7053, NULL, 'RY', '599', '599', 4, 248, 0, NULL),
(313, 'PA1CW', 'UR4EI', '2020-02-01 15:23:00', '0074', '0064', 7053, NULL, 'RY', '599', '599', 4, 288, 0, NULL),
(314, 'PA1CW', 'LY4K', '2020-02-01 15:23:00', '0075', '0048', 7053, NULL, 'RY', '599', '599', 4, 146, 0, NULL),
(315, 'PA1CW', 'OH7L', '2020-02-01 15:24:00', '0076', '0094', 7053, NULL, 'RY', '599', '599', 4, 224, 0, NULL),
(316, 'PA1CW', 'R7RIB', '2020-02-01 15:26:00', '0077', '0030', 7053, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(317, 'PA1CW', 'F4FRC', '2020-02-01 15:27:00', '0078', '0006', 7053, NULL, 'RY', '599', '599', 4, 227, 0, NULL),
(318, 'PA1CW', 'SP6EIY', '2020-02-01 15:28:00', '0079', '0057', 7053, NULL, 'RY', '599', '599', 4, 269, 0, NULL),
(319, 'PA1CW', 'UT5EPP', '2020-02-01 15:29:00', '0080', '0133', 7053, NULL, 'RY', '599', '599', 4, 288, 0, NULL),
(320, 'PA1CW', 'UT7FA', '2020-02-01 15:30:00', '0081', '0098', 7053, NULL, 'RY', '599', '599', 4, 288, 0, NULL),
(321, 'PA1CW', 'R7LY', '2020-02-01 15:31:00', '0082', '0051', 7053, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(322, 'PA1CW', 'SE4E', '2020-02-01 15:33:00', '0083', '0119', 7053, NULL, 'RY', '599', '599', 4, 284, 0, NULL),
(323, 'PA1CW', 'RU2F', '2020-02-01 15:34:00', '0084', '0008', 7053, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(324, 'PA1CW', 'UZ1WW', '2020-02-01 15:34:00', '0085', '0080', 7053, NULL, 'RY', '599', '599', 4, 288, 0, NULL),
(325, 'PA1CW', 'RU6YJ', '2020-02-01 15:36:00', '0086', '0065', 7053, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(326, 'PA1CW', 'EU1DX', '2020-02-01 15:37:00', '0088', '0057', 7053, NULL, 'RY', '599', '599', 4, 27, 0, NULL),
(327, 'PA1CW', 'EA1X', '2020-02-01 15:38:00', '0089', '0067', 7053, NULL, 'RY', '599', '599', 4, 281, 0, NULL),
(328, 'PA1CW', 'I2WIJ', '2020-02-01 15:41:00', '0090', '0077', 7051, NULL, 'RY', '599', '599', 4, 248, 0, NULL),
(329, 'PA1CW', 'SV9MBH', '2020-02-01 15:43:00', '0091', '0027', 7042, NULL, 'RY', '599', '599', 4, 40, 0, NULL),
(330, 'PA1CW', 'ER1PB', '2020-02-01 15:45:00', '0092', '0103', 7044, NULL, 'RY', '599', '599', 4, 179, 0, NULL),
(331, 'PA1CW', 'R1AV', '2020-02-01 15:46:00', '0093', '0048', 7045, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(332, 'PA1CW', 'RT9S', '2020-02-01 15:48:00', '0094', '0094', 7047, NULL, 'RY', '599', '599', 4, 15, 0, NULL),
(333, 'PA1CW', 'SM5IMO', '2020-02-01 15:52:00', '0095', '0122', 7055, NULL, 'RY', '599', '599', 4, 284, 0, NULL),
(334, 'PA1CW', 'S58Q', '2020-02-01 15:54:00', '0096', '0045', 7053, NULL, 'RY', '599', '599', 4, 499, 0, NULL),
(335, 'PA1CW', 'OH2LNH', '2020-02-01 15:55:00', '0097', '0051', 7056, NULL, 'RY', '599', '599', 4, 224, 0, NULL),
(336, 'PA1CW', 'F4FDR', '2020-02-01 15:58:00', '0098', '0108', 7047, NULL, 'RY', '599', '599', 4, 227, 0, NULL),
(337, 'PA1CW', 'OM3RM', '2020-02-01 16:01:00', '0099', '0155', 7043, NULL, 'RY', '599', '599', 4, 504, 0, NULL),
(338, 'PA1CW', 'RN1AO', '2020-02-01 16:09:00', '0100', '0030', 7047, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(339, 'PA1CW', 'LY2CG', '2020-02-01 16:10:00', '0101', '0047', 7047, NULL, 'RY', '599', '599', 4, 146, 0, NULL),
(340, 'PA1CW', 'LY2PAD', '2020-02-01 16:11:00', '0102', '0058', 7047, NULL, 'RY', '599', '599', 4, 146, 0, NULL),
(341, 'PA1CW', 'R7MT', '2020-02-01 16:12:00', '0103', '0076', 7047, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(342, 'PA1CW', 'RU5X', '2020-02-01 16:13:00', '0104', '0048', 7047, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(343, 'PA1CW', 'PA5WT', '2020-02-01 16:14:00', '0105', '0090', 7047, NULL, 'RY', '599', '599', 4, 263, 0, NULL),
(344, 'PA1CW', 'R2XM', '2020-02-01 16:17:00', '0106', '0054', 7047, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(345, 'PA1CW', 'IW1CBG', '2020-02-01 16:17:00', '0107', '0028', 7047, NULL, 'RY', '599', '599', 4, 248, 0, NULL),
(346, 'PA1CW', 'RN2FQ', '2020-02-01 16:19:00', '0108', '0007', 7047, NULL, 'RY', '599', '599', 4, 126, 0, NULL),
(347, 'PA1CW', 'EW8OM', '2020-02-01 16:20:00', '0109', '0012', 7047, NULL, 'RY', '599', '599', 4, 27, 0, NULL),
(348, 'PA1CW', 'RA1ALC', '2020-02-01 16:21:00', '0110', '0069', 7047, NULL, 'RY', '599', '599', 4, 54, 0, NULL),
(349, 'PD5PET', 'UA9CTT', '2020-02-01 12:46:00', '001', '34', 14086, NULL, 'RY', '599', '599', 5, 15, 0, 24),
(350, 'PD5PET', 'OH3FM', '2020-02-01 12:49:00', '002', '070', 14086, NULL, 'RY', '599', '599', 5, 224, 0, 24),
(351, 'PD5PET', 'PA3JQD', '2020-02-01 12:51:00', '003', '019', 14094, NULL, 'RY', '599', '599', 5, 263, 0, 24),
(352, 'PD5PET', 'LZ5K', '2020-02-01 12:56:00', '004', '18', 14083, NULL, 'RY', '599', '599', 5, 212, 0, 24),
(353, 'PD5PET', 'ER1PB', '2020-02-01 12:59:00', '005', '030', 14083, NULL, 'RY', '599', '599', 5, 179, 0, 24),
(354, 'PD5PET', 'UA6Y', '2020-02-01 13:06:00', '006', '048', 14099, NULL, 'RY', '599', '599', 5, 54, 0, 25),
(355, 'PD5PET', 'PG5V', '2020-02-01 13:09:00', '007', '003', 14098, NULL, 'RY', '599', '599', 5, 263, 0, 25),
(356, 'PD5PET', 'EU8A', '2020-02-01 13:15:00', '008', '019', 14089, NULL, 'RY', '599', '599', 5, 27, 0, 24),
(357, 'PD5PET', 'R2XM', '2020-02-01 13:16:00', '009', '012', 14087, NULL, 'RY', '599', '599', 5, 54, 0, 24),
(358, 'PD5PET', 'US0ZK', '2020-02-01 13:20:00', '010', '024', 14102, NULL, 'RY', '599', '599', 5, 288, 0, 27),
(359, 'PD5PET', 'PA3JB', '2020-02-01 13:25:00', '011', '009', 14098, NULL, 'RY', '599', '599', 5, 263, 0, 25),
(360, 'PD5PET', 'YO9BCM', '2020-02-01 13:32:00', '012', '046', 14098, NULL, 'RY', '599', '599', 5, 275, 0, 25),
(361, 'PD5PET', 'EW6DM', '2020-02-01 13:42:00', '013', '059', 14099, NULL, 'RY', '599', '599', 5, 27, 0, 25),
(362, 'PD5PET', 'UX7QV', '2020-02-01 13:49:00', '014', '069', 14093, NULL, 'RY', '599', '599', 5, 288, 0, 24),
(363, 'PD5PET', 'UT5EPP', '2020-02-01 13:50:00', '015', '075', 14092, NULL, 'RY', '599', '599', 5, 288, 0, 24),
(364, 'PD5PET', 'PA3ACA', '2020-02-01 13:55:00', '016', '026', 14090, NULL, 'RY', '599', '599', 5, 263, 0, 24),
(365, 'PD5PET', 'YO6HSU', '2020-02-01 14:01:00', '017', '065', 14090, NULL, 'RY', '599', '599', 5, 275, 0, 24),
(366, 'PD5PET', 'UR4EI', '2020-02-01 14:02:00', '018', '038', 14089, NULL, 'RY', '599', '599', 5, 288, 0, 24),
(367, 'PD5PET', 'PD2PKM', '2020-02-01 14:05:00', '019', '058', 14096, NULL, 'RY', '599', '599', 5, 263, 0, 25),
(368, 'PD5PET', 'F4DSK', '2020-02-01 14:17:00', '020', '005', 7046, NULL, 'RY', '599', '599', 5, 227, 0, NULL),
(369, 'PD5PET', 'EA4GOY', '2020-02-01 14:18:00', '021', '069', 7047, NULL, 'RY', '599', '599', 5, 281, 0, NULL),
(370, 'PD5PET', 'DL5KUD', '2020-02-01 14:18:00', '022', '032', 7048, NULL, 'RY', '599', '599', 5, 230, 0, NULL),
(371, 'PD5PET', 'LY2SA', '2020-02-01 14:22:00', '023', '032', 7042, NULL, 'RY', '599', '599', 5, 146, 0, NULL),
(372, 'PD5PET', 'OZ4NE', '2020-02-01 14:24:00', '024', '039', 7041, NULL, 'RY', '599', '599', 5, 221, 0, NULL),
(373, 'PD5PET', 'DL4ME', '2020-02-01 14:26:00', '025', '014', 7045, NULL, 'RY', '599', '599', 5, 230, 0, NULL),
(374, 'PD5PET', 'DJ3IW', '2020-02-01 14:27:00', '026', '046', 7045, NULL, 'RY', '599', '599', 5, 230, 0, NULL),
(375, 'PD5PET', 'N6AR', '2020-02-01 14:38:00', '027', '83', 14099, NULL, 'RY', '599', '599', 5, 291, 0, 25),
(376, 'PD5PET', 'K4FX', '2020-02-01 14:49:00', '028', '114', 14088, NULL, 'RY', '599', '599', 5, 291, 0, 24),
(377, 'PD5PET', 'PD3JSB', '2020-02-01 14:53:00', '029', '005', 14087, NULL, 'RY', '599', '599', 5, 263, 0, 24),
(378, 'PD5PET', 'YO7LYM', '2020-02-01 14:56:00', '030', '019', 14087, NULL, 'RY', '599', '599', 5, 275, 0, 24),
(379, 'PD5PET', 'YO3GNF', '2020-02-01 15:03:00', '031', '006', 14087, NULL, 'RY', '599', '599', 5, 275, 0, 24),
(380, 'PD5PET', 'PA0CMF', '2020-02-01 15:06:00', '032', '030', 14087, NULL, 'RY', '599', '599', 5, 263, 0, 24),
(381, 'PD5PET', 'EB5F', '2020-02-01 15:17:00', '033', '009', 14087, NULL, 'RY', '599', '599', 5, 281, 0, 24),
(382, 'PD5PET', 'IK7RVY', '2020-02-01 15:20:00', '034', '108', 14087, NULL, 'RY', '599', '599', 5, 248, 0, 24),
(383, 'AB5XM', 'W3FIZ', '2020-02-01 16:23:00', '0001', '0209', 14820, NULL, 'RY', '599', '599', 6, NULL, NULL, NULL),
(384, 'AB5XM', 'KB8O', '2020-02-01 16:32:00', '0002', '0095', 14084, NULL, 'RY', '599', '599', 6, NULL, NULL, NULL),
(385, 'AB5XM', 'WW6RY', '2020-02-01 17:53:00', '0003', '0055', 14093, NULL, 'RY', '599', '599', 6, NULL, NULL, NULL),
(386, 'AB5XM', 'K9OM', '2020-02-01 18:00:00', '0004', '0211', 14084, NULL, 'RY', '599', '599', 6, NULL, NULL, NULL),
(387, 'AB5XM', 'KC4SA', '2020-02-01 18:02:00', '0005', '0094', 14088, NULL, 'RY', '599', '599', 6, NULL, NULL, NULL),
(388, 'AB5XM', 'WA4JQS', '2020-02-01 18:05:00', '0006', '0022', 14090, NULL, 'RY', '599', '599', 6, NULL, NULL, NULL),
(389, 'AB5XM', 'AI9T', '2020-02-01 20:05:00', '0007', '0144', 14082, NULL, 'RY', '599', '599', 6, NULL, NULL, NULL),
(390, 'AB5XM', 'K7OM', '2020-02-01 21:44:00', '0008', '0014', 14091, NULL, 'RY', '599', '599', 6, NULL, NULL, NULL),
(391, 'AB5XM', 'W3FIZ', '2020-02-01 16:23:00', '0001', '0209', 14082, NULL, 'RY', '599', '599', 7, 291, 0, 24),
(392, 'AB5XM', 'KB8O', '2020-02-01 16:32:00', '0002', '0095', 14084, NULL, 'RY', '599', '599', 7, 291, 0, 24),
(393, 'AB5XM', 'WW6RY', '2020-02-01 17:53:00', '0003', '0055', 14093, NULL, 'RY', '599', '599', 7, 291, 0, 24),
(394, 'AB5XM', 'K9OM', '2020-02-01 18:00:00', '0004', '0211', 14084, NULL, 'RY', '599', '599', 7, 291, 0, 24),
(395, 'AB5XM', 'KC4SA', '2020-02-01 18:02:00', '0005', '0094', 14088, NULL, 'RY', '599', '599', 7, 291, 0, 24),
(396, 'AB5XM', 'WA4JQS', '2020-02-01 18:05:00', '0006', '0022', 14090, NULL, 'RY', '599', '599', 7, 291, 0, 24),
(397, 'AB5XM', 'AI9T', '2020-02-01 20:05:00', '0007', '0144', 14082, NULL, 'RY', '599', '599', 7, 291, 0, 24),
(398, 'AB5XM', 'K7OM', '2020-02-01 21:44:00', '0008', '0014', 14091, NULL, 'RY', '599', '599', 7, 291, 0, 24),
(399, 'KA2AEY', 'K9OM', '2020-02-01 19:48:00', '0001', '0241', 14084, NULL, 'RY', '599', '599', 8, 291, 0, 24),
(400, 'KA2AEY', 'NG7M', '2020-02-01 19:50:00', '0002', '0012', 14093, NULL, 'RY', '599', '599', 8, 291, 0, 24),
(401, 'KA2AEY', 'VO1CH', '2020-02-01 20:38:00', '0003', '0005', 14091, NULL, 'RY', '599', '599', 8, 1, 0, 24),
(402, 'KA2AEY', 'KN4BIT', '2020-02-01 20:44:00', '0004', '0128', 14102, NULL, 'RY', '599', '599', 8, 291, 0, 27),
(403, 'KA2AEY', 'VE6TK', '2020-02-01 22:56:00', '0005', '0027', 14093, NULL, 'RY', '599', '599', 8, 1, 0, 24),
(404, 'KA2AEY', 'K6HGF', '2020-02-01 23:04:00', '0006', '0072', 14091, NULL, 'RY', '599', '599', 8, 291, 0, 24),
(405, 'KA2AEY', 'XE1CT', '2020-02-01 23:10:00', '0007', 'GTO', 14087, NULL, 'RY', '599', '599', 8, 50, 0, 24),
(406, 'KA2AEY', 'KN5S', '2020-02-01 23:12:00', '0008', '0018', 14093, NULL, 'RY', '599', '599', 8, 291, 0, 24),
(407, 'KA2AEY', 'WE6EZ', '2020-02-01 23:35:00', '0009', '0016', 14083, NULL, 'RY', '599', '599', 8, 291, 0, 24),
(408, 'K2MK', 'N8OO', '2020-02-01 18:41:00', '0001', '0015', 7050, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(409, 'K2MK', 'AI9T', '2020-02-01 18:42:00', '0002', '0013', 7049, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(410, 'K2MK', 'N5HC', '2020-02-01 18:42:00', '0003', '0018', 7045, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(411, 'K2MK', 'WW6RY', '2020-02-01 18:43:00', '0004', '0008', 7046, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(412, 'K2MK', 'W7OM', '2020-02-01 18:48:00', '0005', '0004', 7044, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(413, 'K2MK', 'LZ5K', '2020-02-01 18:51:00', '0006', '0011', 14084, NULL, 'RY', '599', '599', 9, 212, 0, 24),
(414, 'K2MK', 'S51CK', '2020-02-01 18:54:00', '0007', '0024', 14091, NULL, 'RY', '599', '599', 9, 499, 0, 24),
(415, 'K2MK', 'HG5D', '2020-02-01 18:57:00', '0008', '0039', 14098, NULL, 'RY', '599', '599', 9, 239, 0, 25),
(416, 'K2MK', 'YL5T', '2020-02-01 18:58:00', '0009', '0010', 14099, NULL, 'RY', '599', '599', 9, 145, 0, 25),
(417, 'K2MK', 'SM5IMO', '2020-02-01 19:00:00', '0010', '0028', 14104, NULL, 'RY', '599', '599', 9, 284, 0, 27),
(418, 'K2MK', 'DQ9Y', '2020-02-01 19:02:00', '0011', '0034', 14094, NULL, 'RY', '599', '599', 9, 230, 0, 24),
(419, 'K2MK', 'KS0AA', '2020-02-01 19:05:00', '0012', '0008', 7043, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(420, 'K2MK', 'AA5AU', '2020-02-01 19:06:00', '0013', '0004', 7047, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(421, 'K2MK', 'N2BJ', '2020-02-01 19:06:00', '0014', '0004', 7050, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(422, 'K2MK', 'WB5BHS', '2020-02-01 19:37:00', '0015', '0010', 7053, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(423, 'K2MK', 'OM3RM', '2020-02-01 19:41:00', '0016', '0034', 14091, NULL, 'RY', '599', '599', 9, 504, 0, 24),
(424, 'K2MK', 'K1SM', '2020-02-01 19:42:00', '0017', '0060', 14107, NULL, 'RY', '599', '599', 9, 291, 0, 27),
(425, 'K2MK', 'LY5W', '2020-02-01 20:14:00', '0018', '0042', 14112, NULL, 'RY', '599', '599', 9, 146, 0, 27),
(426, 'K2MK', 'S53X', '2020-02-01 20:23:00', '0019', '0068', 14084, NULL, 'RY', '599', '599', 9, 499, 0, 24),
(427, 'K2MK', 'XE1CT', '2020-02-01 20:25:00', '0020', 'GTO', 14090, NULL, 'RY', '599', '599', 9, 50, 0, 24),
(428, 'K2MK', 'N5HC', '2020-02-01 20:26:00', '0021', '0032', 14091, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(429, 'K2MK', 'PA7WW', '2020-02-01 20:30:00', '0022', '0091', 14103, NULL, 'RY', '599', '599', 9, 263, 0, 27),
(430, 'K2MK', 'PA3DUU', '2020-02-01 20:31:00', '0023', '0056', 14108, NULL, 'RY', '599', '599', 9, 263, 0, 27),
(431, 'K2MK', 'DK0OF', '2020-02-01 20:32:00', '0024', '0070', 14113, NULL, 'RY', '599', '599', 9, 230, 0, NULL),
(432, 'K2MK', 'NU1T', '2020-02-01 20:34:00', '0025', '0029', 14116, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(433, 'K2MK', 'TG9ADQ', '2020-02-01 20:50:00', '0026', '0012', 14087, NULL, 'RY', '599', '599', 9, 76, 0, 24),
(434, 'K2MK', 'SP3LGF', '2020-02-01 20:53:00', '0027', '0050', 14096, NULL, 'RY', '599', '599', 9, 269, 0, 25),
(435, 'K2MK', 'ND9G', '2020-02-01 20:53:00', '0028', '0009', 14095, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(436, 'K2MK', 'I4JED', '2020-02-01 20:54:00', '0029', '0027', 14098, NULL, 'RY', '599', '599', 9, 248, 0, 25),
(437, 'K2MK', '9A6A', '2020-02-01 20:56:00', '0030', '0079', 14102, NULL, 'RY', '599', '599', 9, 497, 0, 27),
(438, 'K2MK', 'I2WIJ', '2020-02-01 21:28:00', '0031', '0049', 14088, NULL, 'RY', '599', '599', 9, 248, 0, 24),
(439, 'K2MK', 'KI6DY', '2020-02-01 21:29:00', '0032', '0032', 14090, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(440, 'K2MK', 'IK6VXO', '2020-02-01 21:30:00', '0033', '0024', 14095, NULL, 'RY', '599', '599', 9, 248, 0, 24),
(441, 'K2MK', 'F1RHS', '2020-02-01 21:51:00', '0034', '0074', 14086, NULL, 'RY', '599', '599', 9, 227, 0, 24);
INSERT INTO `TBL_CONTEST_QSO` (`N_ID_CONTEST_QSO`, `S_CALLSIGN_EMISOR`, `S_CALLSIGN_RECEPTOR`, `D_DATETIME`, `S_EXCHANGE_EMMITED`, `S_EXCHANGE_RECEIVED`, `N_FREQUENCY`, `S_CATEGORY_OPERATOR`, `S_MODE`, `S_RST_EMMITED`, `S_RST_RECEIVED`, `N_ID_CONTEST_LOG`, `D_ENTITY_CODE`, `D_DXCC_NOT_FOUND`, `N_ID_FREQUENCY_BAND`) VALUES
(442, 'K2MK', 'KS0AA', '2020-02-01 21:53:00', '0035', '0039', 14090, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(443, 'K2MK', 'DJ5MW', '2020-02-01 21:54:00', '0036', '0054', 14095, NULL, 'RY', '599', '599', 9, 230, 0, 24),
(444, 'K2MK', 'NX8G', '2020-02-01 21:56:00', '0037', '0059', 14103, NULL, 'RY', '599', '599', 9, 291, 0, 27),
(445, 'K2MK', 'N3XL', '2020-02-01 21:57:00', '0038', '0034', 14108, NULL, 'RY', '599', '599', 9, 291, 0, 27),
(446, 'K2MK', 'K2YG', '2020-02-01 22:16:00', '0038', '0012', 14112, NULL, 'RY', '599', '599', 9, 291, 0, 27),
(447, 'K2MK', 'XE2YWB', '2020-02-01 22:19:00', '0039', 'ZAC', 14110, NULL, 'RY', '599', '599', 9, 50, 0, 27),
(448, 'K2MK', 'W3FIZ', '2020-02-01 22:22:00', '0040', '0208', 14087, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(449, 'K2MK', 'AD4TJ', '2020-02-01 22:24:00', '0041', '0066', 14091, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(450, 'K2MK', 'W4LC', '2020-02-01 22:28:00', '0042', '0063', 14099, NULL, 'RY', '599', '599', 9, 291, 0, 25),
(451, 'K2MK', 'VE7BC', '2020-02-02 01:00:00', '0043', '0000', 14088, NULL, 'RY', '599', '599', 9, 1, 0, 24),
(452, 'K2MK', 'W0IZ', '2020-02-02 01:02:00', '0044', '0027', 14089, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(453, 'K2MK', 'WA2MCR', '2020-02-02 01:04:00', '0045', '0013', 14093, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(454, 'K2MK', 'YV4ABR', '2020-02-02 01:08:00', '0046', '0026', 14095, NULL, 'RY', '599', '599', 9, 148, 0, 24),
(455, 'K2MK', 'VE2AXO', '2020-02-02 01:08:00', '0047', '0010', 14095, NULL, 'RY', '599', '599', 9, 1, 0, 24),
(456, 'K2MK', 'VE2BVV', '2020-02-02 01:10:00', '0048', '0077', 14096, NULL, 'RY', '599', '599', 9, 1, 0, 25),
(457, 'K2MK', 'WA2QAU', '2020-02-02 01:11:00', '0049', '0021', 14096, NULL, 'RY', '599', '599', 9, 291, 0, 25),
(458, 'K2MK', 'W2WB', '2020-02-02 01:13:00', '0050', '0029', 14096, NULL, 'RY', '599', '599', 9, 291, 0, 25),
(459, 'K2MK', 'NX0I', '2020-02-02 01:21:00', '0051', '0003', 14100, NULL, 'RY', '599', '599', 9, 291, 0, 26),
(460, 'K2MK', 'W9FY', '2020-02-02 01:23:00', '0052', '0010', 14089, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(461, 'K2MK', 'W6SX', '2020-02-02 01:23:00', '0053', '0062', 14089, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(462, 'K2MK', 'K5WW', '2020-02-02 01:25:00', '0054', '0008', 14089, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(463, 'K2MK', 'N2HMM', '2020-02-02 01:27:00', '0055', '0025', 14088, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(464, 'K2MK', 'KP4/K6DTT', '2020-02-02 01:28:00', '0056', '0029', 14098, NULL, 'RY', '599', '599', 9, 291, 0, 25),
(465, 'K2MK', 'N2BJ', '2020-02-02 01:29:00', '0057', '0083', 14090, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(466, 'K2MK', '4A5E', '2020-02-02 01:29:00', '0058', 'QRO', 14084, NULL, 'RY', '599', '599', 9, 50, 0, 24),
(467, 'K2MK', 'KF6RY', '2020-02-02 01:30:00', '0059', '0040', 14094, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(468, 'K2MK', 'WW6RY', '2020-02-02 01:31:00', '0060', '0079', 14093, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(469, 'K2MK', 'KE6QR', '2020-02-02 01:32:00', '0061', '0038', 14093, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(470, 'K2MK', 'YV5AAX', '2020-02-02 01:32:00', '0062', '0074', 14102, NULL, 'RY', '599', '599', 9, 148, 0, 27),
(471, 'K2MK', 'KB0HP', '2020-02-02 01:58:00', '0063', '0018', 14088, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(472, 'K2MK', 'WX8C', '2020-02-02 01:58:00', '0064', '0008', 14088, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(473, 'K2MK', 'VE2FK', '2020-02-02 01:59:00', '0065', '0178', 14088, NULL, 'RY', '599', '599', 9, 1, 0, 24),
(474, 'K2MK', 'NA2NY', '2020-02-02 01:59:00', '0066', '0013', 14088, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(475, 'K2MK', 'N8WCP', '2020-02-02 02:00:00', '0067', '0011', 14088, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(476, 'K2MK', 'N5YHF', '2020-02-02 02:02:00', '0068', '0007', 14088, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(477, 'K2MK', 'WB2MLY', '2020-02-02 02:03:00', '0069', '0020', 14088, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(478, 'K2MK', 'W9ILY', '2020-02-02 02:07:00', '0070', '0094', 14088, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(479, 'K2MK', 'NG7M', '2020-02-02 02:07:00', '0071', '0026', 14088, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(480, 'K2MK', 'XE2N', '2020-02-02 02:08:00', '0072', 'NL', 14088, NULL, 'RY', '599', '599', 9, 50, 0, 24),
(481, 'K2MK', 'WP3E', '2020-02-02 02:11:00', '0073', '0003', 14089, NULL, 'RY', '599', '599', 9, 202, 0, 24),
(482, 'K2MK', 'W1JXN', '2020-02-02 03:46:00', '0074', '0002', 14090, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(483, 'K2MK', 'W7PU', '2020-02-02 03:51:00', '0075', '0041', 14091, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(484, 'K2MK', 'XE2AU', '2020-02-02 03:51:00', '0076', 'AGS', 14092, NULL, 'RY', '599', '599', 9, 50, 0, 24),
(485, 'K2MK', 'XE2YWH', '2020-02-02 03:53:00', '0077', 'ZAC', 14096, NULL, 'RY', '599', '599', 9, 50, 0, 25),
(486, 'K2MK', 'W7JHR', '2020-02-02 03:56:00', '0078', '0017', 14096, NULL, 'RY', '599', '599', 9, 291, 0, 25),
(487, 'K2MK', 'N2WK', '2020-02-02 03:57:00', '0079', '0063', 14096, NULL, 'RY', '599', '599', 9, 291, 0, 25),
(488, 'K2MK', 'VE3EP', '2020-02-02 03:58:00', '0080', '0012', 14096, NULL, 'RY', '599', '599', 9, 1, 0, 25),
(489, 'K2MK', 'WD0BGZ', '2020-02-02 03:59:00', '0081', '0019', 14096, NULL, 'RY', '599', '599', 9, 291, 0, 25),
(490, 'K2MK', 'K7VAP', '2020-02-02 04:01:00', '0082', '0009', 14096, NULL, 'RY', '599', '599', 9, 291, 0, 25),
(491, 'K2MK', 'XE1VY', '2020-02-02 04:01:00', '0083', 'MIC', 14084, NULL, 'RY', '599', '599', 9, 50, 0, 24),
(492, 'K2MK', 'K6HGF', '2020-02-02 05:51:00', '0084', '0098', 14084, NULL, 'RY', '599', '599', 9, 291, 0, 24),
(493, 'K2MK', 'N7NM', '2020-02-02 05:53:00', '0085', '0095', 14102, NULL, 'RY', '599', '599', 9, 291, 0, 27),
(494, 'K2MK', 'W3FIZ', '2020-02-02 05:54:00', '0086', '0354', 7045, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(495, 'K2MK', '4A5E', '2020-02-02 05:57:00', '0087', 'QRO', 7056, NULL, 'RY', '599', '599', 9, 50, 0, NULL),
(496, 'K2MK', 'KI6DY', '2020-02-02 05:58:00', '0088', '0140', 7059, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(497, 'K2MK', 'W3RGA', '2020-02-02 05:58:00', '0089', '0023', 7060, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(498, 'K2MK', 'VE2AXO', '2020-02-02 05:59:00', '0090', '0050', 7060, NULL, 'RY', '599', '599', 9, 1, 0, NULL),
(499, 'K2MK', 'CO8BYT', '2020-02-02 06:00:00', '0091', '0068', 7062, NULL, 'RY', '599', '599', 9, 70, 0, NULL),
(500, 'K2MK', 'S51CK', '2020-02-02 06:08:00', '0092', '0359', 7047, NULL, 'RY', '599', '599', 9, 499, 0, NULL),
(501, 'K2MK', 'VE2FK', '2020-02-02 06:09:00', '0093', '0239', 7055, NULL, 'RY', '599', '599', 9, 1, 0, NULL),
(502, 'K2MK', 'AA4DD', '2020-02-02 06:12:00', '0094', '0074', 7061, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(503, 'K2MK', 'N5YHF', '2020-02-02 06:15:00', '0095', '0026', 7061, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(504, 'K2MK', 'KD5J', '2020-02-02 06:18:00', '0096', '0066', 7053, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(505, 'K2MK', 'N7NM', '2020-02-02 06:20:00', '0097', '0111', 7062, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(506, 'K2MK', '9A6A', '2020-02-02 06:21:00', '0098', '0322', 7042, NULL, 'RY', '599', '599', 9, 497, 0, NULL),
(507, 'K2MK', 'WV8DH', '2020-02-02 06:21:00', '0099', '0018', 7054, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(508, 'K2MK', 'XE2YWH', '2020-02-02 06:24:00', '0100', 'ZAC', 7063, NULL, 'RY', '599', '599', 9, 50, 0, NULL),
(509, 'K2MK', 'W3FV', '2020-02-02 06:24:00', '0101', '0098', 7054, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(510, 'K2MK', 'K3SV', '2020-02-02 06:25:00', '0102', '0050', 7045, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(511, 'K2MK', 'WA7CPA', '2020-02-02 06:25:00', '0103', '0020', 7053, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(512, 'K2MK', 'KB8O', '2020-02-02 06:27:00', '0104', '0267', 7055, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(513, 'K2MK', 'N4STG', '2020-02-02 06:28:00', '0105', '0016', 7058, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(514, 'K2MK', 'NX5O', '2020-02-02 06:34:00', '0106', '0017', 7067, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(515, 'K2MK', 'KP4/K6DTT', '2020-02-02 06:37:00', '0107', '0215', 7056, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(516, 'K2MK', 'N5KWN', '2020-02-02 06:39:00', '0108', '0098', 7048, NULL, 'RY', '599', '599', 9, 291, 0, NULL),
(517, 'K2MK', 'OM3RM', '2020-02-02 06:40:00', '0109', '0371', 7055, NULL, 'RY', '599', '599', 9, 504, 0, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_EDITION`
--

CREATE TABLE `TBL_EDITION` (
  `N_ID_EDITION` int(11) NOT NULL,
  `N_ID_CONTEST` int(11) DEFAULT NULL,
  `S_DESCRIPTION` varchar(255) DEFAULT NULL,
  `N_YEAR` int(11) DEFAULT NULL,
  `T_START_UTC` datetime DEFAULT NULL,
  `T_END_UTC` datetime DEFAULT NULL,
  `N_ID_EMAIL_START` int(11) DEFAULT NULL,
  `N_ID_EMAIL_END` int(11) DEFAULT NULL,
  `N_ACTIVE` bit(1) DEFAULT NULL,
  `S_TEMPLATE` varchar(255) DEFAULT NULL,
  `S_QSO_PARSER_IMPL` varchar(255) DEFAULT NULL,
  `S_QSO_VALIDATION_IMPL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_EDITION`
--

INSERT INTO `TBL_EDITION` (`N_ID_EDITION`, `N_ID_CONTEST`, `S_DESCRIPTION`, `N_YEAR`, `T_START_UTC`, `T_END_UTC`, `N_ID_EMAIL_START`, `N_ID_EMAIL_END`, `N_ACTIVE`, `S_TEMPLATE`, `S_QSO_PARSER_IMPL`, `S_QSO_VALIDATION_IMPL`) VALUES
(1, 1, 'Edición 2020', 2020, '2020-02-01 12:00:00', '2020-02-03 00:00:00', 3679, NULL, b'1', 'email_template_rtty_2020', 'qsoParserRtty2020ServiceImpl', 'evaluateQsoRtty2020');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_EMAIL`
--

CREATE TABLE `TBL_EMAIL` (
  `N_ID_EMAIL` int(11) NOT NULL,
  `N_EMAIL_COUNT` int(11) DEFAULT NULL,
  `N_ID_EDITION` int(11) DEFAULT NULL,
  `S_RECIPIENTS_FROM_NAME` varchar(255) DEFAULT NULL,
  `S_RECIPIENTS_FROM_ADDRESS` varchar(255) DEFAULT NULL,
  `D_RECEIVED_DATE` datetime DEFAULT NULL,
  `S_RECIPIENTS_TO` varchar(255) DEFAULT NULL,
  `D_SENT_DATE` datetime DEFAULT NULL,
  `S_SUBJECT` varchar(255) DEFAULT NULL,
  `N_ID_EMAIL_STATUS` int(11) DEFAULT NULL,
  `VERIFIED_AT` datetime DEFAULT NULL,
  `ANSWERED_AT` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_EMAIL`
--

INSERT INTO `TBL_EMAIL` (`N_ID_EMAIL`, `N_EMAIL_COUNT`, `N_ID_EDITION`, `S_RECIPIENTS_FROM_NAME`, `S_RECIPIENTS_FROM_ADDRESS`, `D_RECEIVED_DATE`, `S_RECIPIENTS_TO`, `D_SENT_DATE`, `S_SUBJECT`, `N_ID_EMAIL_STATUS`, `VERIFIED_AT`, `ANSWERED_AT`) VALUES
(1, 3679, 1, 'TECHNOLOGY MÉXICO - Coaching y Capacitación Empresarial', 'info@ventas.arctm.net', '2020-01-30 02:37:12', 'rtty@fmre.mx <rtty@fmre.mx>', '2020-01-30 02:36:19', '- TALLER PRÁCTICO: Aplicación, Optimización y Dominio de Excel para Profesionistas - CDMX - Febrero', 4, '2020-02-01 07:04:36', '2020-02-03 02:16:31'),
(2, 3680, 1, 'Sevco Finance', 'Mwaqstfgvbh@indosat.net.id', '2020-01-31 06:39:04', NULL, '2020-01-31 06:32:10', 'Re:', 4, '2020-02-01 07:04:36', '2020-02-03 02:22:51'),
(3, 3681, 1, 'Mail Delivery System', 'postmaster-im2@indosatm2.com', '2020-02-01 07:05:20', 'rtty@fmre.mx', '2020-02-01 07:04:36', 'Delivery Status Notification (Failure)', 4, '2020-02-01 07:10:36', '2020-02-03 02:22:52'),
(4, 3682, 1, 'PERLIS DX AMATUER RADIO CLUB', 'perlisdx@gmail.com', '2020-02-01 16:46:03', 'rtty@fmre.mx', '2020-02-01 16:45:32', '9M4CPS', 5, '2020-02-01 16:46:36', '2020-02-03 02:22:53'),
(5, 3683, 1, 'Theo Koning', 'pa1cw@hotmail.com', '2020-02-01 16:46:04', 'rtty@fmre.mx <rtty@fmre.mx>', '2020-02-01 16:45:47', 'PA1CW', 5, '2020-02-01 16:46:36', '2020-02-03 02:23:49'),
(6, 3684, 1, 'Theo Koning', 'pa1cw@hotmail.com', '2020-02-01 16:58:26', 'rtty@fmre.mx <rtty@fmre.mx>', '2020-02-01 16:58:11', 'pa1cw', 5, '2020-02-01 16:59:36', '2020-02-03 02:23:51'),
(7, 3685, 1, 'Theo Koning', 'pa1cw@hotmail.com', '2020-02-01 16:59:29', 'rtty@fmre.mx <rtty@fmre.mx>', '2020-02-01 16:59:15', 'pa1cw', 5, '2020-02-01 17:00:36', '2020-02-03 02:23:52'),
(8, 3686, 1, 'Олег Стрибный', 'rd1a@mail.ru', '2020-02-01 19:22:46', 'rtty@fmre.mx', '2020-02-01 19:22:31', 'RD1A', 4, '2020-02-01 19:23:36', '2020-02-03 02:24:49'),
(9, 3687, 1, 'peter van der waal', 'petervanderwaal77@gmail.com', '2020-02-01 19:52:10', 'rtty@fmre.mx', '2020-02-01 19:51:46', 'LOG XE RTTY 2020', 5, '2020-02-01 19:53:36', '2020-02-03 02:24:51'),
(10, 3688, 1, 'asnorthmain@yahoo.com', 'asnorthmain@yahoo.com', '2020-02-01 23:20:01', 'rtty@fmre.mx <rtty@fmre.mx>', '2020-02-01 23:19:40', 'AB5XM', 5, '2020-02-01 23:21:36', '2020-02-03 02:24:52'),
(11, 3689, 1, 'asnorthmain@yahoo.com', 'asnorthmain@yahoo.com', '2020-02-01 23:33:03', 'rtty@fmre.mx <rtty@fmre.mx>', '2020-02-01 23:32:40', 'AB5XM', 5, '2020-02-01 23:33:36', '2020-02-03 02:25:49'),
(12, 3690, 1, 'Joe Kernaghan', 'ka2aey@gmail.com', '2020-02-01 23:42:09', 'rtty@fmre.mx', '2020-02-01 23:41:55', 'KA2AEY', 5, '2020-02-01 23:43:36', '2020-02-03 02:25:51'),
(13, 3691, 1, 'nagahama noriyuki', 'norijm2lei599@gmail.com', '2020-02-01 23:51:11', 'rtty@fmre.mx', '2020-02-01 23:50:41', 'XE RTTY 2020 LOG de JM2LEI', 5, '2020-02-02 06:06:10', '2020-02-03 02:25:52'),
(14, 3692, 1, 'K2MK', 'k2mk.mike@gmail.com', '2020-02-02 00:44:21', 'rtty@fmre.mx', '2020-02-02 00:43:50', 'k2mk', 4, '2020-02-02 00:45:36', '2020-02-03 02:26:49'),
(15, 3693, 1, 'K2MK', 'k2mk.mike@gmail.com', '2020-02-02 00:57:53', 'rtty@fmre.mx', '2020-02-02 00:57:24', 'k2mk', 5, '2020-02-02 06:23:36', '2020-02-03 02:26:50');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_EMAIL_ACCOUNT`
--

CREATE TABLE `TBL_EMAIL_ACCOUNT` (
  `N_ID_EMAIL_ACCOUNT` int(11) NOT NULL,
  `N_ID_CONTEST` int(11) DEFAULT NULL,
  `S_IN_HOST` varchar(255) DEFAULT NULL,
  `S_OUT_HOST` varchar(255) DEFAULT NULL,
  `S_EMAIL_ADDRESS` varchar(255) DEFAULT NULL,
  `S_USERNAME` varchar(255) DEFAULT NULL,
  `S_PASSWORD` varchar(255) DEFAULT NULL,
  `N_IN_PORT` int(11) DEFAULT NULL,
  `N_OUT_PORT` int(11) DEFAULT NULL,
  `S_REPLY_TO_NAME` varchar(255) DEFAULT NULL,
  `S_REPLY_TO_EMAIL` varchar(255) DEFAULT NULL,
  `SUCCESSFULLY_RESPONSE_MSG` varchar(255) DEFAULT NULL,
  `ERROR_RESPONSE_MSG` varchar(255) DEFAULT NULL,
  `S_SMTP_PROPERTIES` varchar(600) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_EMAIL_ACCOUNT`
--

INSERT INTO `TBL_EMAIL_ACCOUNT` (`N_ID_EMAIL_ACCOUNT`, `N_ID_CONTEST`, `S_IN_HOST`, `S_OUT_HOST`, `S_EMAIL_ADDRESS`, `S_USERNAME`, `S_PASSWORD`, `N_IN_PORT`, `N_OUT_PORT`, `S_REPLY_TO_NAME`, `S_REPLY_TO_EMAIL`, `SUCCESSFULLY_RESPONSE_MSG`, `ERROR_RESPONSE_MSG`, `S_SMTP_PROPERTIES`) VALUES
(1, 1, 'imap.fmre.mx', 'just101.justhost.com', 'rtty@fmre.mx', 'rtty@fmre.mx', 'aJX24Yxiljgg16SmL6YJyA==', 993, 465, 'XE1EE', 'rttymanager@fmre.mx', 'Correo recibido correctamente / Email received correctly', 'Email recibido con errores / Email received with errors', 'mail.transport.protocol:smtp;mail.smtp.auth:true;mail.debug:true;mail.smtp.socketFactory.port:465;mail.smtp.socketFactory.class:javax.net.ssl.SSLSocketFactory;mail.smtp.socketFactory.fallback:false');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_QSO_CONTEO`
--

CREATE TABLE `TBL_QSO_CONTEO` (
  `N_ID_REL_QSO_CONTEO` int(11) NOT NULL,
  `N_ID_CONTEST_QSO` int(11) DEFAULT NULL,
  `N_ID_CONTEO` int(11) DEFAULT NULL,
  `D_DATETIME` datetime NOT NULL,
  `N_POINTS` int(11) DEFAULT NULL,
  `IS_COMPLETE` int(1) NOT NULL,
  `N_IS_MULTIPLY` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_QSO_CONTEO`
--

INSERT INTO `TBL_QSO_CONTEO` (`N_ID_REL_QSO_CONTEO`, `N_ID_CONTEST_QSO`, `N_ID_CONTEO`, `D_DATETIME`, `N_POINTS`, `IS_COMPLETE`, `N_IS_MULTIPLY`) VALUES
(1, 1, 1, '2020-02-18 12:29:43', 3, 1, 1),
(2, 2, 1, '2020-02-18 12:29:43', 3, 1, 1),
(3, 3, 1, '2020-02-18 12:29:43', 3, 1, 1),
(4, 4, 1, '2020-02-18 12:29:43', 3, 1, 1),
(5, 5, 1, '2020-02-18 12:29:43', 3, 1, 1),
(6, 6, 1, '2020-02-18 12:29:43', 3, 1, 1),
(7, 7, 1, '2020-02-18 12:29:43', 3, 1, 1),
(8, 8, 1, '2020-02-18 12:29:43', 3, 1, 0),
(9, 9, 1, '2020-02-18 12:29:43', 3, 0, 1),
(10, 10, 1, '2020-02-18 12:29:43', 3, 0, 1),
(11, 11, 1, '2020-02-18 12:29:43', 3, 0, 1),
(12, 12, 1, '2020-02-18 12:29:43', 3, 0, 0),
(13, 13, 1, '2020-02-18 12:29:43', 3, 0, 1),
(14, 14, 1, '2020-02-18 12:29:43', 3, 0, 0),
(15, 15, 1, '2020-02-18 12:29:43', 3, 0, 1),
(16, 16, 1, '2020-02-18 12:29:43', 3, 1, 1),
(17, 17, 1, '2020-02-18 12:29:43', 3, 0, 1),
(18, 18, 1, '2020-02-18 12:29:43', 3, 0, 1),
(19, 19, 1, '2020-02-18 12:29:43', 3, 0, 1),
(20, 20, 1, '2020-02-18 12:29:43', 3, 0, 1),
(21, 21, 1, '2020-02-18 12:29:43', 3, 0, 0),
(22, 22, 1, '2020-02-18 12:29:43', 3, 0, 1),
(23, 23, 1, '2020-02-18 12:29:43', 3, 0, 0),
(24, 24, 1, '2020-02-18 12:29:43', 3, 0, 1),
(25, 241, 1, '2020-02-18 12:29:43', 3, 1, 1),
(26, 242, 1, '2020-02-18 12:29:43', 3, 1, 1),
(27, 243, 1, '2020-02-18 12:29:43', 3, 1, 1),
(28, 244, 1, '2020-02-18 12:29:43', 3, 1, 1),
(29, 245, 1, '2020-02-18 12:29:43', 3, 1, 1),
(30, 246, 1, '2020-02-18 12:29:43', 3, 1, 1),
(31, 247, 1, '2020-02-18 12:29:43', 3, 1, 1),
(32, 248, 1, '2020-02-18 12:29:43', 3, 1, 1),
(33, 249, 1, '2020-02-18 12:29:43', 3, 1, 1),
(34, 250, 1, '2020-02-18 12:29:43', 3, 1, 0),
(35, 251, 1, '2020-02-18 12:29:43', 3, 1, 1),
(36, 252, 1, '2020-02-18 12:29:43', 3, 1, 0),
(37, 253, 1, '2020-02-18 12:29:43', 3, 1, 1),
(38, 254, 1, '2020-02-18 12:29:43', 3, 1, 0),
(39, 255, 1, '2020-02-18 12:29:43', 3, 1, 1),
(40, 256, 1, '2020-02-18 12:29:43', 3, 1, 0),
(41, 257, 1, '2020-02-18 12:29:43', 3, 1, 0),
(42, 258, 1, '2020-02-18 12:29:43', 3, 1, 1),
(43, 259, 1, '2020-02-18 12:29:43', 3, 1, 0),
(44, 260, 1, '2020-02-18 12:29:43', 3, 1, 1),
(45, 261, 1, '2020-02-18 12:29:43', 3, 1, 0),
(46, 262, 1, '2020-02-18 12:29:43', 3, 1, 1),
(47, 263, 1, '2020-02-18 12:29:43', 3, 1, 0),
(48, 264, 1, '2020-02-18 12:29:43', 3, 1, 1),
(49, 265, 1, '2020-02-18 12:29:43', 3, 1, 1),
(50, 266, 1, '2020-02-18 12:29:43', 3, 1, 1),
(51, 267, 1, '2020-02-18 12:29:43', 3, 1, 1),
(52, 268, 1, '2020-02-18 12:29:43', 3, 1, 1),
(53, 269, 1, '2020-02-18 12:29:43', 3, 1, 1),
(54, 270, 1, '2020-02-18 12:29:43', 3, 1, 1),
(55, 271, 1, '2020-02-18 12:29:43', 3, 1, 1),
(56, 272, 1, '2020-02-18 12:29:43', 3, 1, 1),
(57, 273, 1, '2020-02-18 12:29:43', 3, 1, 1),
(58, 274, 1, '2020-02-18 12:29:43', 3, 1, 0),
(59, 275, 1, '2020-02-18 12:29:43', 3, 1, 1),
(60, 276, 1, '2020-02-18 12:29:43', 3, 0, 0),
(61, 277, 1, '2020-02-18 12:29:43', 3, 0, 0),
(62, 278, 1, '2020-02-18 12:29:43', 3, 0, 1),
(63, 279, 1, '2020-02-18 12:29:43', 3, 0, 0),
(64, 280, 1, '2020-02-18 12:29:43', 3, 0, 0),
(65, 281, 1, '2020-02-18 12:29:43', 3, 0, 1),
(66, 282, 1, '2020-02-18 12:29:43', 3, 0, 0),
(67, 283, 1, '2020-02-18 12:29:43', 3, 0, 1),
(68, 284, 1, '2020-02-18 12:29:43', 3, 0, 1),
(69, 285, 1, '2020-02-18 12:29:43', 3, 0, 0),
(70, 286, 1, '2020-02-18 12:29:43', 3, 0, 1),
(71, 287, 1, '2020-02-18 12:29:43', 3, 0, 0),
(72, 288, 1, '2020-02-18 12:29:43', 3, 0, 1),
(73, 289, 1, '2020-02-18 12:29:43', 3, 0, 0),
(74, 290, 1, '2020-02-18 12:29:43', 3, 0, 0),
(75, 291, 1, '2020-02-18 12:29:43', 3, 0, 1),
(76, 292, 1, '2020-02-18 12:29:43', 3, 0, 1),
(77, 293, 1, '2020-02-18 12:29:43', 3, 0, 0),
(78, 294, 1, '2020-02-18 12:29:43', 3, 0, 1),
(79, 295, 1, '2020-02-18 12:29:43', 3, 0, 1),
(80, 296, 1, '2020-02-18 12:29:43', 3, 0, 0),
(81, 297, 1, '2020-02-18 12:29:43', 3, 0, 1),
(82, 298, 1, '2020-02-18 12:29:43', 3, 1, 1),
(83, 299, 1, '2020-02-18 12:29:43', 3, 1, 0),
(84, 300, 1, '2020-02-18 12:29:43', 3, 1, 1),
(85, 301, 1, '2020-02-18 12:29:43', 3, 1, 1),
(86, 302, 1, '2020-02-18 12:29:43', 3, 1, 0),
(87, 303, 1, '2020-02-18 12:29:43', 3, 1, 1),
(88, 304, 1, '2020-02-18 12:29:43', 3, 1, 1),
(89, 305, 1, '2020-02-18 12:29:43', 3, 1, 1),
(90, 306, 1, '2020-02-18 12:29:43', 3, 0, 1),
(91, 307, 1, '2020-02-18 12:29:43', 3, 0, 1),
(92, 308, 1, '2020-02-18 12:29:43', 3, 0, 1),
(93, 309, 1, '2020-02-18 12:29:43', 3, 0, 0),
(94, 310, 1, '2020-02-18 12:29:43', 3, 0, 0),
(95, 311, 1, '2020-02-18 12:29:43', 3, 0, 1),
(96, 312, 1, '2020-02-18 12:29:43', 3, 0, 1),
(97, 313, 1, '2020-02-18 12:29:43', 3, 0, 1),
(98, 314, 1, '2020-02-18 12:29:43', 3, 0, 0),
(99, 315, 1, '2020-02-18 12:29:43', 3, 0, 1),
(100, 316, 1, '2020-02-18 12:29:44', 3, 0, 0),
(101, 317, 1, '2020-02-18 12:29:44', 3, 0, 0),
(102, 318, 1, '2020-02-18 12:29:44', 3, 0, 0),
(103, 319, 1, '2020-02-18 12:29:44', 3, 0, 1),
(104, 320, 1, '2020-02-18 12:29:44', 3, 0, 0),
(105, 321, 1, '2020-02-18 12:29:44', 3, 0, 0),
(106, 322, 1, '2020-02-18 12:29:44', 3, 0, 1),
(107, 323, 1, '2020-02-18 12:29:44', 3, 0, 0),
(108, 324, 1, '2020-02-18 12:29:44', 3, 0, 1),
(109, 325, 1, '2020-02-18 12:29:44', 3, 0, 1),
(110, 326, 1, '2020-02-18 12:29:44', 3, 0, 0),
(111, 327, 1, '2020-02-18 12:29:44', 3, 0, 0),
(112, 328, 1, '2020-02-18 12:29:44', 3, 0, 1),
(113, 329, 1, '2020-02-18 12:29:44', 3, 0, 1),
(114, 330, 1, '2020-02-18 12:29:44', 3, 0, 0),
(115, 331, 1, '2020-02-18 12:29:44', 3, 0, 0),
(116, 332, 1, '2020-02-18 12:29:44', 3, 0, 0),
(117, 333, 1, '2020-02-18 12:29:44', 3, 0, 1),
(118, 334, 1, '2020-02-18 12:29:44', 3, 0, 1),
(119, 335, 1, '2020-02-18 12:29:44', 3, 0, 0),
(120, 336, 1, '2020-02-18 12:29:44', 3, 0, 1),
(121, 337, 1, '2020-02-18 12:29:44', 3, 0, 1),
(122, 338, 1, '2020-02-18 12:29:44', 3, 0, 0),
(123, 339, 1, '2020-02-18 12:29:44', 3, 0, 1),
(124, 340, 1, '2020-02-18 12:29:44', 3, 0, 1),
(125, 341, 1, '2020-02-18 12:29:44', 3, 0, 1),
(126, 342, 1, '2020-02-18 12:29:44', 3, 0, 0),
(127, 343, 1, '2020-02-18 12:29:44', 3, 0, 1),
(128, 344, 1, '2020-02-18 12:29:44', 3, 0, 1),
(129, 345, 1, '2020-02-18 12:29:44', 3, 0, 1),
(130, 346, 1, '2020-02-18 12:29:44', 3, 0, 0),
(131, 347, 1, '2020-02-18 12:29:44', 3, 0, 0),
(132, 348, 1, '2020-02-18 12:29:44', 3, 0, 1),
(133, 349, 1, '2020-02-18 12:29:44', 3, 1, 1),
(134, 350, 1, '2020-02-18 12:29:44', 3, 1, 1),
(135, 351, 1, '2020-02-18 12:29:44', 3, 1, 1),
(136, 352, 1, '2020-02-18 12:29:44', 3, 1, 1),
(137, 353, 1, '2020-02-18 12:29:44', 3, 1, 1),
(138, 354, 1, '2020-02-18 12:29:44', 3, 1, 1),
(139, 355, 1, '2020-02-18 12:29:44', 3, 1, 1),
(140, 356, 1, '2020-02-18 12:29:44', 3, 1, 0),
(141, 357, 1, '2020-02-18 12:29:44', 3, 1, 1),
(142, 358, 1, '2020-02-18 12:29:44', 3, 1, 1),
(143, 359, 1, '2020-02-18 12:29:44', 3, 1, 1),
(144, 360, 1, '2020-02-18 12:29:44', 3, 1, 1),
(145, 361, 1, '2020-02-18 12:29:44', 3, 1, 1),
(146, 362, 1, '2020-02-18 12:29:44', 3, 1, 1),
(147, 363, 1, '2020-02-18 12:29:44', 3, 1, 1),
(148, 364, 1, '2020-02-18 12:29:44', 3, 1, 1),
(149, 365, 1, '2020-02-18 12:29:44', 3, 1, 1),
(150, 366, 1, '2020-02-18 12:29:44', 3, 1, 1),
(151, 367, 1, '2020-02-18 12:29:44', 3, 1, 1),
(152, 368, 1, '2020-02-18 12:29:44', 3, 0, 1),
(153, 369, 1, '2020-02-18 12:29:44', 3, 0, 0),
(154, 370, 1, '2020-02-18 12:29:44', 3, 0, 1),
(155, 371, 1, '2020-02-18 12:29:44', 3, 0, 0),
(156, 372, 1, '2020-02-18 12:29:44', 3, 0, 1),
(157, 373, 1, '2020-02-18 12:29:44', 3, 0, 1),
(158, 374, 1, '2020-02-18 12:29:44', 3, 0, 0),
(159, 375, 1, '2020-02-18 12:29:44', 3, 1, 1),
(160, 376, 1, '2020-02-18 12:29:44', 3, 1, 1),
(161, 377, 1, '2020-02-18 12:29:44', 3, 1, 0),
(162, 378, 1, '2020-02-18 12:29:44', 3, 1, 0),
(163, 379, 1, '2020-02-18 12:29:44', 3, 1, 1),
(164, 380, 1, '2020-02-18 12:29:44', 3, 1, 0),
(165, 381, 1, '2020-02-18 12:29:44', 3, 1, 0),
(166, 382, 1, '2020-02-18 12:29:44', 3, 1, 1),
(167, 391, 1, '2020-02-18 12:29:44', 3, 1, 1),
(168, 392, 1, '2020-02-18 12:29:44', 3, 1, 1),
(169, 393, 1, '2020-02-18 12:29:44', 3, 1, 1),
(170, 394, 1, '2020-02-18 12:29:44', 3, 1, 1),
(171, 395, 1, '2020-02-18 12:29:44', 3, 1, 1),
(172, 396, 1, '2020-02-18 12:29:44', 3, 1, 1),
(173, 397, 1, '2020-02-18 12:29:44', 3, 1, 1),
(174, 398, 1, '2020-02-18 12:29:44', 3, 1, 1),
(175, 399, 1, '2020-02-18 12:29:44', 3, 1, 1),
(176, 400, 1, '2020-02-18 12:29:44', 3, 1, 1),
(177, 401, 1, '2020-02-18 12:29:44', 3, 1, 1),
(178, 402, 1, '2020-02-18 12:29:44', 3, 1, 1),
(179, 403, 1, '2020-02-18 12:29:44', 3, 1, 1),
(180, 404, 1, '2020-02-18 12:29:44', 3, 1, 1),
(181, 405, 1, '2020-02-18 12:29:44', 3, 1, 1),
(182, 406, 1, '2020-02-18 12:29:44', 3, 1, 1),
(183, 407, 1, '2020-02-18 12:29:44', 3, 1, 1),
(184, 408, 1, '2020-02-18 12:29:44', 3, 0, 1),
(185, 409, 1, '2020-02-18 12:29:44', 3, 0, 1),
(186, 410, 1, '2020-02-18 12:29:44', 3, 0, 1),
(187, 411, 1, '2020-02-18 12:29:44', 3, 0, 1),
(188, 412, 1, '2020-02-18 12:29:44', 3, 0, 1),
(189, 413, 1, '2020-02-18 12:29:44', 3, 1, 1),
(190, 414, 1, '2020-02-18 12:29:44', 3, 1, 1),
(191, 415, 1, '2020-02-18 12:29:44', 3, 1, 1),
(192, 416, 1, '2020-02-18 12:29:44', 3, 1, 1),
(193, 417, 1, '2020-02-18 12:29:44', 3, 1, 1),
(194, 418, 1, '2020-02-18 12:29:44', 3, 1, 1),
(195, 419, 1, '2020-02-18 12:29:45', 3, 0, 0),
(196, 420, 1, '2020-02-18 12:29:45', 3, 0, 0),
(197, 421, 1, '2020-02-18 12:29:45', 3, 0, 0),
(198, 422, 1, '2020-02-18 12:29:45', 3, 0, 0),
(199, 423, 1, '2020-02-18 12:29:45', 3, 1, 0),
(200, 424, 1, '2020-02-18 12:29:45', 3, 1, 1),
(201, 425, 1, '2020-02-18 12:29:45', 3, 1, 1),
(202, 426, 1, '2020-02-18 12:29:45', 3, 1, 1),
(203, 427, 1, '2020-02-18 12:29:45', 3, 1, 1),
(204, 428, 1, '2020-02-18 12:29:45', 3, 1, 1),
(205, 429, 1, '2020-02-18 12:29:45', 3, 1, 1),
(206, 430, 1, '2020-02-18 12:29:45', 3, 1, 1),
(207, 431, 1, '2020-02-18 12:29:45', 3, 0, 1),
(208, 432, 1, '2020-02-18 12:29:45', 3, 0, 1),
(209, 433, 1, '2020-02-18 12:29:45', 3, 1, 1),
(210, 434, 1, '2020-02-18 12:29:45', 3, 1, 1),
(211, 435, 1, '2020-02-18 12:29:45', 3, 1, 1),
(212, 436, 1, '2020-02-18 12:29:45', 3, 1, 1),
(213, 437, 1, '2020-02-18 12:29:45', 3, 1, 1),
(214, 438, 1, '2020-02-18 12:29:45', 3, 1, 1),
(215, 439, 1, '2020-02-18 12:29:45', 3, 1, 0),
(216, 440, 1, '2020-02-18 12:29:45', 3, 1, 0),
(217, 441, 1, '2020-02-18 12:29:45', 3, 1, 1),
(218, 442, 1, '2020-02-18 12:29:45', 3, 1, 0),
(219, 443, 1, '2020-02-18 12:29:45', 3, 1, 1),
(220, 444, 1, '2020-02-18 12:29:45', 3, 1, 1),
(221, 445, 1, '2020-02-18 12:29:45', 3, 1, 0),
(222, 446, 1, '2020-02-18 12:29:45', 3, 1, 0),
(223, 447, 1, '2020-02-18 12:29:45', 3, 1, 1),
(224, 448, 1, '2020-02-18 12:29:45', 3, 1, 1),
(225, 449, 1, '2020-02-18 12:29:45', 3, 1, 1),
(226, 450, 1, '2020-02-18 12:29:45', 3, 1, 1),
(227, 451, 1, '2020-02-18 12:29:45', 3, 1, 1),
(228, 452, 1, '2020-02-18 12:29:45', 3, 1, 0),
(229, 453, 1, '2020-02-18 12:29:45', 3, 1, 0),
(230, 454, 1, '2020-02-18 12:29:45', 3, 1, 1),
(231, 455, 1, '2020-02-18 12:29:45', 3, 1, 0),
(232, 456, 1, '2020-02-18 12:29:45', 3, 1, 1),
(233, 457, 1, '2020-02-18 12:29:45', 3, 1, 1),
(234, 458, 1, '2020-02-18 12:29:45', 3, 1, 0),
(235, 459, 1, '2020-02-18 12:29:45', 3, 1, 1),
(236, 460, 1, '2020-02-18 12:29:45', 3, 1, 0),
(237, 461, 1, '2020-02-18 12:29:45', 3, 1, 1),
(238, 462, 1, '2020-02-18 12:29:45', 3, 1, 0),
(239, 463, 1, '2020-02-18 12:29:45', 3, 1, 1),
(240, 464, 1, '2020-02-18 12:29:45', 3, 1, 0),
(241, 465, 1, '2020-02-18 12:29:45', 3, 1, 1),
(242, 466, 1, '2020-02-18 12:29:45', 3, 1, 1),
(243, 467, 1, '2020-02-18 12:29:45', 3, 1, 1),
(244, 468, 1, '2020-02-18 12:29:45', 3, 1, 0),
(245, 469, 1, '2020-02-18 12:29:45', 3, 1, 1),
(246, 470, 1, '2020-02-18 12:29:45', 3, 1, 0),
(247, 471, 1, '2020-02-18 12:29:45', 3, 1, 0),
(248, 472, 1, '2020-02-18 12:29:45', 3, 1, 0),
(249, 473, 1, '2020-02-18 12:29:45', 3, 1, 1),
(250, 474, 1, '2020-02-18 12:29:45', 3, 1, 0),
(251, 475, 1, '2020-02-18 12:29:45', 3, 1, 0),
(252, 476, 1, '2020-02-18 12:29:45', 3, 1, 1),
(253, 477, 1, '2020-02-18 12:29:45', 3, 1, 1),
(254, 478, 1, '2020-02-18 12:29:45', 3, 1, 1),
(255, 479, 1, '2020-02-18 12:29:45', 3, 1, 0),
(256, 480, 1, '2020-02-18 12:29:45', 3, 1, 1),
(257, 481, 1, '2020-02-18 12:29:45', 3, 1, 0),
(258, 482, 1, '2020-02-18 12:29:45', 3, 1, 1),
(259, 483, 1, '2020-02-18 12:29:45', 3, 1, 1),
(260, 484, 1, '2020-02-18 12:29:45', 3, 1, 1),
(261, 485, 1, '2020-02-18 12:29:45', 3, 1, 0),
(262, 486, 1, '2020-02-18 12:29:45', 3, 1, 1),
(263, 487, 1, '2020-02-18 12:29:45', 3, 1, 0),
(264, 488, 1, '2020-02-18 12:29:45', 3, 1, 0),
(265, 489, 1, '2020-02-18 12:29:45', 3, 1, 1),
(266, 490, 1, '2020-02-18 12:29:45', 3, 1, 0),
(267, 491, 1, '2020-02-18 12:29:45', 3, 1, 1),
(268, 492, 1, '2020-02-18 12:29:45', 3, 1, 1),
(269, 493, 1, '2020-02-18 12:29:45', 3, 1, 1),
(270, 494, 1, '2020-02-18 12:29:45', 3, 0, 1),
(271, 495, 1, '2020-02-18 12:29:45', 3, 0, 0),
(272, 496, 1, '2020-02-18 12:29:45', 3, 0, 1),
(273, 497, 1, '2020-02-18 12:29:45', 3, 0, 1),
(274, 498, 1, '2020-02-18 12:29:45', 3, 0, 0),
(275, 499, 1, '2020-02-18 12:29:45', 3, 0, 0),
(276, 500, 1, '2020-02-18 12:29:45', 3, 0, 1),
(277, 501, 1, '2020-02-18 12:29:45', 3, 0, 1),
(278, 502, 1, '2020-02-18 12:29:45', 3, 0, 0),
(279, 503, 1, '2020-02-18 12:29:45', 3, 0, 0),
(280, 504, 1, '2020-02-18 12:29:45', 3, 0, 0),
(281, 505, 1, '2020-02-18 12:29:45', 3, 0, 1),
(282, 506, 1, '2020-02-18 12:29:45', 3, 0, 1),
(283, 507, 1, '2020-02-18 12:29:45', 3, 0, 0),
(284, 508, 1, '2020-02-18 12:29:45', 3, 0, 0),
(285, 509, 1, '2020-02-18 12:29:45', 3, 0, 0),
(286, 510, 1, '2020-02-18 12:29:45', 3, 0, 0),
(287, 511, 1, '2020-02-18 12:29:45', 3, 0, 0),
(288, 512, 1, '2020-02-18 12:29:45', 3, 0, 1),
(289, 513, 1, '2020-02-18 12:29:46', 3, 0, 1),
(290, 514, 1, '2020-02-18 12:29:46', 3, 0, 0),
(291, 515, 1, '2020-02-18 12:29:46', 3, 0, 1),
(292, 516, 1, '2020-02-18 12:29:46', 3, 0, 0),
(293, 517, 1, '2020-02-18 12:29:46', 3, 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_ROLES`
--

CREATE TABLE `TBL_ROLES` (
  `N_ID_ROLE` int(11) NOT NULL,
  `S_ROLE` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_ROLES`
--

INSERT INTO `TBL_ROLES` (`N_ID_ROLE`, `S_ROLE`) VALUES
(1, 'USER');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TBL_USERS`
--

CREATE TABLE `TBL_USERS` (
  `N_ID_USER` int(11) NOT NULL,
  `S_EMAIL` varchar(255) DEFAULT NULL,
  `S_PASSWORD` varchar(255) DEFAULT NULL,
  `S_NAME` varchar(255) DEFAULT NULL,
  `S_LAST_NAME` varchar(255) DEFAULT NULL,
  `B_ACTIVE` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `TBL_USERS`
--

INSERT INTO `TBL_USERS` (`N_ID_USER`, `S_EMAIL`, `S_PASSWORD`, `S_NAME`, `S_LAST_NAME`, `B_ACTIVE`) VALUES
(1, 'admin@admin.com', '$2a$10$jLpHkSywOB9WhE5Se3nLH.KfC2Pi6H7wHblaya0xx34nK970o6qYa', 'adminname', 'adminlastname', b'1');

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `V_LAST_EMAIL`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `V_LAST_EMAIL` (
`EDITION_ID` int(11)
,`EMAIL_ID` int(11)
,`N_ID_CONTEST_LOG` int(11)
,`EMAIL_SUBJECT` varchar(255)
,`EMAIL_STATUS` varchar(255)
);

-- --------------------------------------------------------

--
-- Estructura para la vista `V_LAST_EMAIL`
--
DROP TABLE IF EXISTS `V_LAST_EMAIL`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `V_LAST_EMAIL`  AS  (select `TBL_A`.`EDITION_ID` AS `EDITION_ID`,`TBL_A`.`EMAIL_ID` AS `EMAIL_ID`,`LOG`.`N_ID_CONTEST_LOG` AS `N_ID_CONTEST_LOG`,`TBL_A`.`EMAIL_SUBJECT` AS `EMAIL_SUBJECT`,`TBL_A`.`EMAIL_STATUS` AS `EMAIL_STATUS` from (`TBL_CONTEST_LOG` `LOG` join (select `EMAIL`.`S_SUBJECT` AS `EMAIL_SUBJECT`,max(`EMAIL`.`N_ID_EMAIL`) AS `EMAIL_ID`,`ST`.`S_STATUS` AS `EMAIL_STATUS`,`EDITION`.`N_ID_EDITION` AS `EDITION_ID` from ((`TBL_EMAIL` `EMAIL` join `CAT_EMAIL_STATUS` `ST` on((`ST`.`N_ID_EMAIL_STATUS` = `EMAIL`.`N_ID_EMAIL_STATUS`))) join `TBL_EDITION` `EDITION` on((`EDITION`.`N_ID_EDITION` = `EMAIL`.`N_ID_EDITION`))) where ((`EMAIL`.`S_SUBJECT` <> '') and (`EMAIL`.`S_SUBJECT` is not null) and (`EMAIL`.`VERIFIED_AT` is not null) and (`EMAIL`.`ANSWERED_AT` is not null) and (`ST`.`S_STATUS` = 'PARSED')) group by `EMAIL`.`S_SUBJECT`,`EDITION`.`N_ID_EDITION`) `TBL_A` on((`TBL_A`.`EMAIL_ID` = `LOG`.`N_ID_EMAIL`)))) ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `CAT_DXCC_ENTITY`
--
ALTER TABLE `CAT_DXCC_ENTITY`
  ADD PRIMARY KEY (`D_ENTITY_CODE`);

--
-- Indices de la tabla `CAT_DXCC_SESSION`
--
ALTER TABLE `CAT_DXCC_SESSION`
  ADD PRIMARY KEY (`N_ID_DXCC_SESSION`);

--
-- Indices de la tabla `CAT_EMAIL_ERROR`
--
ALTER TABLE `CAT_EMAIL_ERROR`
  ADD PRIMARY KEY (`N_ID_CAT_EMAIL_ERROR`),
  ADD KEY `CONSTRAINT_FK_CAT_EMAIL_ERROR` (`N_ID_EDITION`);

--
-- Indices de la tabla `CAT_EMAIL_STATUS`
--
ALTER TABLE `CAT_EMAIL_STATUS`
  ADD PRIMARY KEY (`N_ID_EMAIL_STATUS`);

--
-- Indices de la tabla `CAT_FREQUENCY_BAND`
--
ALTER TABLE `CAT_FREQUENCY_BAND`
  ADD PRIMARY KEY (`N_ID_FREQUENCY_BAND`);

--
-- Indices de la tabla `CAT_QSO_ERROR`
--
ALTER TABLE `CAT_QSO_ERROR`
  ADD PRIMARY KEY (`N_ID_QSO_ERROR`),
  ADD KEY `CONSTRAINT_FK_CAT_QSO_ERROR` (`N_ID_EDITION`);

--
-- Indices de la tabla `REL_CONTEO_CONTEST_LOG`
--
ALTER TABLE `REL_CONTEO_CONTEST_LOG`
  ADD PRIMARY KEY (`N_ID_REL_CONTEO_CONTEST_LOG`),
  ADD KEY `CONSTRAINT_FK_REL_CONTEO_CONTEST_LOG_CONTEO` (`N_ID_CONTEO`),
  ADD KEY `CONSTRAINT_FK_REL_CONTEO_CONTEST_LOG_CONTEST_LOG` (`N_ID_CONTEST_LOG`);

--
-- Indices de la tabla `REL_EMAIL_EMAIL_ERROR`
--
ALTER TABLE `REL_EMAIL_EMAIL_ERROR`
  ADD PRIMARY KEY (`N_ID_CAT_EMAIL_ERROR`,`N_ID_EMAIL`),
  ADD KEY `CONSTRAINT_FK_REL_EMAIL_EMAIL_ERROR_TBL_EMAIL` (`N_ID_EMAIL`);

--
-- Indices de la tabla `REL_QSO_CONTEO_QSO_ERROR`
--
ALTER TABLE `REL_QSO_CONTEO_QSO_ERROR`
  ADD PRIMARY KEY (`ID_REL_QSO_CONTEO_QSO_ERROR`),
  ADD KEY `CONSTRAINT_FK_REL_QSO_CONTEO` (`N_ID_REL_QSO_CONTEO`),
  ADD KEY `CONSTRAINT_FK_QSO_ERROR` (`N_ID_QSO_ERROR`);

--
-- Indices de la tabla `REL_USER_ROLE`
--
ALTER TABLE `REL_USER_ROLE`
  ADD PRIMARY KEY (`N_ID_ROLE`,`N_ID_USER`),
  ADD KEY `CONSTRAINT_FK_USER_ROLE_USER` (`N_ID_USER`);

--
-- Indices de la tabla `TBL_ATTACHED_FILE`
--
ALTER TABLE `TBL_ATTACHED_FILE`
  ADD PRIMARY KEY (`N_ID_ATTACHED_FILE`),
  ADD KEY `CONSTRAINT_FK_TBL_EMAIL` (`N_ID_EMAIL`);

--
-- Indices de la tabla `TBL_CONTEO`
--
ALTER TABLE `TBL_CONTEO`
  ADD PRIMARY KEY (`N_ID_CONTEO`),
  ADD KEY `CONSTRAINT_FK_EDITION` (`N_ID_EDITION`);

--
-- Indices de la tabla `TBL_CONTEST`
--
ALTER TABLE `TBL_CONTEST`
  ADD PRIMARY KEY (`N_ID_CONTEST`);

--
-- Indices de la tabla `TBL_CONTEST_LOG`
--
ALTER TABLE `TBL_CONTEST_LOG`
  ADD PRIMARY KEY (`N_ID_CONTEST_LOG`),
  ADD KEY `CONSTRAINT_FK_TBL_CONTEST_LOG_TBL_EMAIL` (`N_ID_EMAIL`),
  ADD KEY `D_ENTITY_CODE` (`D_ENTITY_CODE`);

--
-- Indices de la tabla `TBL_CONTEST_QSO`
--
ALTER TABLE `TBL_CONTEST_QSO`
  ADD PRIMARY KEY (`N_ID_CONTEST_QSO`),
  ADD KEY `CONSTRAINT_FK_TBL_CONTEST_QSO_TBL_CONTEST_LOG` (`N_ID_CONTEST_LOG`),
  ADD KEY `D_ENTITY_CODE` (`D_ENTITY_CODE`),
  ADD KEY `CONSTRAINT_FK_FREQUENCY_BAND` (`N_ID_FREQUENCY_BAND`);

--
-- Indices de la tabla `TBL_EDITION`
--
ALTER TABLE `TBL_EDITION`
  ADD PRIMARY KEY (`N_ID_EDITION`),
  ADD KEY `CONSTRAINT_FK_EDITION_CONTEST` (`N_ID_CONTEST`);

--
-- Indices de la tabla `TBL_EMAIL`
--
ALTER TABLE `TBL_EMAIL`
  ADD PRIMARY KEY (`N_ID_EMAIL`),
  ADD KEY `CONSTRAINT_FK_TBL_EDITION` (`N_ID_EDITION`),
  ADD KEY `CONSTRAINT_FK_CAT_EMAIL_STATUS` (`N_ID_EMAIL_STATUS`);

--
-- Indices de la tabla `TBL_EMAIL_ACCOUNT`
--
ALTER TABLE `TBL_EMAIL_ACCOUNT`
  ADD PRIMARY KEY (`N_ID_EMAIL_ACCOUNT`),
  ADD KEY `CONSTRAINT_FK_EMAIL_CONTEST` (`N_ID_CONTEST`);

--
-- Indices de la tabla `TBL_QSO_CONTEO`
--
ALTER TABLE `TBL_QSO_CONTEO`
  ADD PRIMARY KEY (`N_ID_REL_QSO_CONTEO`),
  ADD KEY `CONSTRAINT_FK_CONTEST_QSO` (`N_ID_CONTEST_QSO`),
  ADD KEY `CONSTRAINT_FK_CONTEO` (`N_ID_CONTEO`);

--
-- Indices de la tabla `TBL_ROLES`
--
ALTER TABLE `TBL_ROLES`
  ADD PRIMARY KEY (`N_ID_ROLE`);

--
-- Indices de la tabla `TBL_USERS`
--
ALTER TABLE `TBL_USERS`
  ADD PRIMARY KEY (`N_ID_USER`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `CAT_DXCC_SESSION`
--
ALTER TABLE `CAT_DXCC_SESSION`
  MODIFY `N_ID_DXCC_SESSION` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `CAT_EMAIL_ERROR`
--
ALTER TABLE `CAT_EMAIL_ERROR`
  MODIFY `N_ID_CAT_EMAIL_ERROR` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `CAT_EMAIL_STATUS`
--
ALTER TABLE `CAT_EMAIL_STATUS`
  MODIFY `N_ID_EMAIL_STATUS` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `CAT_FREQUENCY_BAND`
--
ALTER TABLE `CAT_FREQUENCY_BAND`
  MODIFY `N_ID_FREQUENCY_BAND` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;

--
-- AUTO_INCREMENT de la tabla `CAT_QSO_ERROR`
--
ALTER TABLE `CAT_QSO_ERROR`
  MODIFY `N_ID_QSO_ERROR` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `REL_CONTEO_CONTEST_LOG`
--
ALTER TABLE `REL_CONTEO_CONTEST_LOG`
  MODIFY `N_ID_REL_CONTEO_CONTEST_LOG` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `REL_QSO_CONTEO_QSO_ERROR`
--
ALTER TABLE `REL_QSO_CONTEO_QSO_ERROR`
  MODIFY `ID_REL_QSO_CONTEO_QSO_ERROR` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `TBL_ATTACHED_FILE`
--
ALTER TABLE `TBL_ATTACHED_FILE`
  MODIFY `N_ID_ATTACHED_FILE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=769;

--
-- AUTO_INCREMENT de la tabla `TBL_CONTEO`
--
ALTER TABLE `TBL_CONTEO`
  MODIFY `N_ID_CONTEO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `TBL_CONTEST`
--
ALTER TABLE `TBL_CONTEST`
  MODIFY `N_ID_CONTEST` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `TBL_CONTEST_LOG`
--
ALTER TABLE `TBL_CONTEST_LOG`
  MODIFY `N_ID_CONTEST_LOG` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=627;

--
-- AUTO_INCREMENT de la tabla `TBL_CONTEST_QSO`
--
ALTER TABLE `TBL_CONTEST_QSO`
  MODIFY `N_ID_CONTEST_QSO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81158;

--
-- AUTO_INCREMENT de la tabla `TBL_EDITION`
--
ALTER TABLE `TBL_EDITION`
  MODIFY `N_ID_EDITION` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `TBL_EMAIL`
--
ALTER TABLE `TBL_EMAIL`
  MODIFY `N_ID_EMAIL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1267;

--
-- AUTO_INCREMENT de la tabla `TBL_QSO_CONTEO`
--
ALTER TABLE `TBL_QSO_CONTEO`
  MODIFY `N_ID_REL_QSO_CONTEO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=294;

--
-- AUTO_INCREMENT de la tabla `TBL_ROLES`
--
ALTER TABLE `TBL_ROLES`
  MODIFY `N_ID_ROLE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `TBL_USERS`
--
ALTER TABLE `TBL_USERS`
  MODIFY `N_ID_USER` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `CAT_EMAIL_ERROR`
--
ALTER TABLE `CAT_EMAIL_ERROR`
  ADD CONSTRAINT `CONSTRAINT_FK_CAT_EMAIL_ERROR` FOREIGN KEY (`N_ID_EDITION`) REFERENCES `TBL_EDITION` (`N_ID_EDITION`);

--
-- Filtros para la tabla `CAT_QSO_ERROR`
--
ALTER TABLE `CAT_QSO_ERROR`
  ADD CONSTRAINT `CONSTRAINT_FK_CAT_EDITION` FOREIGN KEY (`N_ID_EDITION`) REFERENCES `TBL_EDITION` (`N_ID_EDITION`);

--
-- Filtros para la tabla `REL_CONTEO_CONTEST_LOG`
--
ALTER TABLE `REL_CONTEO_CONTEST_LOG`
  ADD CONSTRAINT `CONSTRAINT_FK_REL_CONTEO_CONTEST_LOG_CONTEO` FOREIGN KEY (`N_ID_CONTEO`) REFERENCES `TBL_CONTEO` (`N_ID_CONTEO`),
  ADD CONSTRAINT `CONSTRAINT_FK_REL_CONTEO_CONTEST_LOG_CONTEST_LOG` FOREIGN KEY (`N_ID_CONTEST_LOG`) REFERENCES `TBL_CONTEST_LOG` (`N_ID_CONTEST_LOG`);

--
-- Filtros para la tabla `REL_EMAIL_EMAIL_ERROR`
--
ALTER TABLE `REL_EMAIL_EMAIL_ERROR`
  ADD CONSTRAINT `CONSTRAINT_FK_REL_EMAIL_EMAIL_ERROR_CAT_EMAIL_ERROR` FOREIGN KEY (`N_ID_CAT_EMAIL_ERROR`) REFERENCES `CAT_EMAIL_ERROR` (`N_ID_CAT_EMAIL_ERROR`),
  ADD CONSTRAINT `CONSTRAINT_FK_REL_EMAIL_EMAIL_ERROR_TBL_EMAIL` FOREIGN KEY (`N_ID_EMAIL`) REFERENCES `TBL_EMAIL` (`N_ID_EMAIL`);

--
-- Filtros para la tabla `REL_QSO_CONTEO_QSO_ERROR`
--
ALTER TABLE `REL_QSO_CONTEO_QSO_ERROR`
  ADD CONSTRAINT `CONSTRAINT_FK_QSO_ERROR` FOREIGN KEY (`N_ID_QSO_ERROR`) REFERENCES `CAT_QSO_ERROR` (`N_ID_QSO_ERROR`),
  ADD CONSTRAINT `CONSTRAINT_FK_REL_QSO_CONTEO` FOREIGN KEY (`N_ID_REL_QSO_CONTEO`) REFERENCES `TBL_QSO_CONTEO` (`N_ID_REL_QSO_CONTEO`);

--
-- Filtros para la tabla `REL_USER_ROLE`
--
ALTER TABLE `REL_USER_ROLE`
  ADD CONSTRAINT `CONSTRAINT_FK_USER_ROLE_ROLE` FOREIGN KEY (`N_ID_ROLE`) REFERENCES `TBL_ROLES` (`N_ID_ROLE`),
  ADD CONSTRAINT `CONSTRAINT_FK_USER_ROLE_USER` FOREIGN KEY (`N_ID_USER`) REFERENCES `TBL_USERS` (`N_ID_USER`);

--
-- Filtros para la tabla `TBL_ATTACHED_FILE`
--
ALTER TABLE `TBL_ATTACHED_FILE`
  ADD CONSTRAINT `CONSTRAINT_FK_TBL_EMAIL` FOREIGN KEY (`N_ID_EMAIL`) REFERENCES `TBL_EMAIL` (`N_ID_EMAIL`);

--
-- Filtros para la tabla `TBL_CONTEO`
--
ALTER TABLE `TBL_CONTEO`
  ADD CONSTRAINT `CONSTRAINT_FK_EDITION` FOREIGN KEY (`N_ID_EDITION`) REFERENCES `TBL_EDITION` (`N_ID_EDITION`);

--
-- Filtros para la tabla `TBL_CONTEST_LOG`
--
ALTER TABLE `TBL_CONTEST_LOG`
  ADD CONSTRAINT `CONSTRAINT_FK_TBL_CONTEST_LOG_TBL_EMAIL` FOREIGN KEY (`N_ID_EMAIL`) REFERENCES `TBL_EMAIL` (`N_ID_EMAIL`),
  ADD CONSTRAINT `TBL_CONTEST_LOG_ibfk_1` FOREIGN KEY (`D_ENTITY_CODE`) REFERENCES `CAT_DXCC_ENTITY` (`D_ENTITY_CODE`);

--
-- Filtros para la tabla `TBL_CONTEST_QSO`
--
ALTER TABLE `TBL_CONTEST_QSO`
  ADD CONSTRAINT `CONSTRAINT_FK_FREQUENCY_BAND` FOREIGN KEY (`N_ID_FREQUENCY_BAND`) REFERENCES `CAT_FREQUENCY_BAND` (`N_ID_FREQUENCY_BAND`),
  ADD CONSTRAINT `CONSTRAINT_FK_TBL_CONTEST_QSO_TBL_CONTEST_LOG` FOREIGN KEY (`N_ID_CONTEST_LOG`) REFERENCES `TBL_CONTEST_LOG` (`N_ID_CONTEST_LOG`),
  ADD CONSTRAINT `TBL_CONTEST_QSO_ibfk_1` FOREIGN KEY (`D_ENTITY_CODE`) REFERENCES `CAT_DXCC_ENTITY` (`D_ENTITY_CODE`);

--
-- Filtros para la tabla `TBL_EDITION`
--
ALTER TABLE `TBL_EDITION`
  ADD CONSTRAINT `CONSTRAINT_FK_EDITION_CONTEST` FOREIGN KEY (`N_ID_CONTEST`) REFERENCES `TBL_CONTEST` (`N_ID_CONTEST`);

--
-- Filtros para la tabla `TBL_EMAIL`
--
ALTER TABLE `TBL_EMAIL`
  ADD CONSTRAINT `CONSTRAINT_FK_CAT_EMAIL_STATUS` FOREIGN KEY (`N_ID_EMAIL_STATUS`) REFERENCES `CAT_EMAIL_STATUS` (`N_ID_EMAIL_STATUS`),
  ADD CONSTRAINT `CONSTRAINT_FK_TBL_EDITION` FOREIGN KEY (`N_ID_EDITION`) REFERENCES `TBL_EDITION` (`N_ID_EDITION`);

--
-- Filtros para la tabla `TBL_EMAIL_ACCOUNT`
--
ALTER TABLE `TBL_EMAIL_ACCOUNT`
  ADD CONSTRAINT `CONSTRAINT_FK_EMAIL_CONTEST` FOREIGN KEY (`N_ID_CONTEST`) REFERENCES `TBL_CONTEST` (`N_ID_CONTEST`);

--
-- Filtros para la tabla `TBL_QSO_CONTEO`
--
ALTER TABLE `TBL_QSO_CONTEO`
  ADD CONSTRAINT `CONSTRAINT_FK_CONTEO` FOREIGN KEY (`N_ID_CONTEO`) REFERENCES `TBL_CONTEO` (`N_ID_CONTEO`),
  ADD CONSTRAINT `CONSTRAINT_FK_CONTEST_QSO` FOREIGN KEY (`N_ID_CONTEST_QSO`) REFERENCES `TBL_CONTEST_QSO` (`N_ID_CONTEST_QSO`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
