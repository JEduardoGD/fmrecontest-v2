CREATE DATABASE fmrecontest CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'fmrecontest'@'%' IDENTIFIED BY 'fmrecontest';
GRANT ALL PRIVILEGES ON fmrecontest.* TO 'fmrecontest'@'%';
