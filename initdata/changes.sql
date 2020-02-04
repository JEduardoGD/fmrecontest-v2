UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'Your email subject contains more than one word; please remember include the subject of your email (that should be only your callsign) and resend it.' WHERE (`N_ID_CAT_EMAIL_ERROR` = '7');

UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'The system found a file, however is unable to parse it. Please make sure your log was created on Cabrillo format, correct and resend it' WHERE (`N_ID_CAT_EMAIL_ERROR` = '6');

UPDATE `CAT_EMAIL_ERROR` SET `S_SUGGESTION_EN` = 'The subject on your Email must be your callsign only, no other text is accepted as stated on the rules. Please correct and resend' WHERE (`N_ID_CAT_EMAIL_ERROR` = '5');
