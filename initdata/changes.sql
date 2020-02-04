UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'Your email subject contains more than one word; please remember include the subject of your email (that should be only your callsign) and resend it.' WHERE (`N_ID_CAT_EMAIL_ERROR` = '7');

UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'The system found a file, however is unable to parse it. Please make sure your log was created on Cabrillo format, correct and resend it' WHERE (`N_ID_CAT_EMAIL_ERROR` = '6');

UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'The subject on your Email must be your callsign only, no other text is accepted as stated on the rules. Please correct and resend' WHERE (`N_ID_CAT_EMAIL_ERROR` = '5');


UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'Your email has no subject, please remember to include the subject of your email (It must be your callsign only) and resend.' WHERE (`N_ID_CAT_EMAIL_ERROR` = '1');
UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'Your email has no attached file, please your logfile in Cabrillo format and resend it.' WHERE (`N_ID_CAT_EMAIL_ERROR` = '2');
UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'Your has attached files but we can\'t determine wich one is your logfile, remember that your log file should be named with a .txt, .log or .cbr extension, please rename and attach your logfile an resend it again.' WHERE (`N_ID_CAT_EMAIL_ERROR` = '3');
UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'The subject on your Email must be your callsign only, no other text is accepted as stated on the rules. Please correct and resend.' WHERE (`N_ID_CAT_EMAIL_ERROR` = '5');
UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'Your email subject has more than one word; the subject must be your callsign only. Correct and resend.' WHERE (`N_ID_CAT_EMAIL_ERROR` = '7');
