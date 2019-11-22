CREATE DATABASE fmrecontest CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'fmrecontest'@'%' IDENTIFIED BY 'fmrecontest';
GRANT ALL PRIVILEGES ON fmrecontest.* TO 'fmrecontest'@'%';
GRANT SELECT ON performance_schema.user_variables_by_thread TO 'fmrecontest'@'%';

