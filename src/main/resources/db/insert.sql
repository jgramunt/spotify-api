USE `SPOTIFY`;

INSERT INTO ARTISTS(ID, NAME) VALUES
	(1, 'The Beatles'),
	(2, 'The Rolling Stones'),
	(3, 'Eagles');

INSERT INTO ALBUMS(ID, NAME) VALUES
	(1, 'Please Please Me'),
	(2, 'Let It Bleed'),
	(3, 'Hell Freezes Over'),
	(4, 'Revolver');

INSERT INTO SONGS(ID, NAME, ARTIST_ID, ALBUM_ID) VALUES
	(1, 'I Saw Her Standing There', 1, 1),
	(2, 'Misery', 1, 1),
	(3, 'Gimme Shelter', 2, 2),
	(4, 'Get Over It', 3, 3),
	(5, 'Taxman', 1, 4);
