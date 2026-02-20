-- =====================================================
-- COMPOSERS
-- =====================================================
INSERT INTO composer
(name, artistic_name, bio, birth, death, place_of_birth, place_of_death, nationality)
VALUES
('Ludwig van Beethoven', NULL,
 'German composer and pianist, crucial figure in the transition between the Classical and Romantic eras.',
 '1770-12-16', '1827-03-26', 'Bonn', 'Vienna', 'German'),

('Wolfgang Amadeus Mozart', NULL,
 'Prolific and influential composer of the Classical period.',
 '1756-01-27', '1791-12-05', 'Salzburg', 'Vienna', 'Austrian'),

('Johann Sebastian Bach', NULL,
 'German composer and musician of the Baroque period.',
 '1685-03-31', '1750-07-28', 'Eisenach', 'Leipzig', 'German'),

('Antonio Vivaldi', 'Il Prete Rosso',
 'Italian Baroque composer, virtuoso violinist and priest.',
 '1678-03-04', '1741-07-28', 'Venice', 'Vienna', 'Italian'),

('Frédéric Chopin', NULL,
 'Polish composer and virtuoso pianist of the Romantic era.',
 '1810-03-01', '1849-10-17', 'Żelazowa Wola', 'Paris', 'Polish');

-- =====================================================
-- PHOTO_COMPOSER
-- =====================================================
INSERT INTO photo_composer
(link, composer_id)
VALUES
('https://example.com/images/beethoven.jpg', 1),
('https://example.com/images/mozart.jpg', 2),
('https://example.com/images/bach.jpg', 3),
('https://example.com/images/vivaldi.jpg', 4),
('https://example.com/images/chopin.jpg', 5);


-- =====================================================
-- MUSICAL_PIECES
-- =====================================================
INSERT INTO musical_piece
(title, alternative_title, instrumentation, premiere, link, composer_id)
VALUES
-- Beethoven
('Symphony No. 5', NULL, 'ORCHESTRA', '1808', 'https://example.com/symphony5', 1),
('Symphony No. 9', 'Choral Symphony', 'ORCHESTRA', '1824', 'https://example.com/symphony9', 1),
('Für Elise', NULL, 'SOLO', '1810', 'https://example.com/furelise', 1),

-- Mozart
('The Magic Flute', 'Die Zauberflöte', 'VOCAL', '1791', 'https://example.com/magicflute', 2),
('Requiem', NULL, 'CHOIR', '1791', 'https://example.com/requiem', 2),
('Symphony No. 40', NULL, 'ORCHESTRA', '1788', 'https://example.com/symphony40', 2),

-- Bach
('Brandenburg Concerto No. 3', NULL, 'CHAMBER', '1721', 'https://example.com/brandenburg3', 3),
('Mass in B minor', NULL, 'CHOIR', '1749', 'https://example.com/massbminor', 3),

-- Vivaldi
('The Four Seasons', NULL, 'ORCHESTRA', '1725', 'https://example.com/fourseasons', 4),
('Gloria', NULL, 'CHOIR', '1715', 'https://example.com/gloria', 4),

-- Chopin
('Nocturne Op. 9 No. 2', NULL, 'SOLO', '1832', 'https://example.com/nocturne', 5),
('Piano Concerto No. 1', NULL, 'ORCHESTRA', '1830', 'https://example.com/pianoconcerto1', 5);


INSERT INTO composer (name, nationality, birth, place_of_birth, bio)
VALUES
('Antonio Vivaldi', 'Italian', '1678-03-04', 'Venice', 'Baroque composer and violinist'),
('Johann Sebastian Bach', 'German', '1685-03-31', 'Eisenach', 'Baroque composer and organist'),
('George Frideric Handel', 'German', '1685-02-23', 'Halle', 'Baroque composer famous for operas and oratorios'),
('Joseph Haydn', 'Austrian', '1732-03-31', 'Rohrau', 'Classical composer and symphonist'),
('Wolfgang Amadeus Mozart', 'Austrian', '1756-01-27', 'Salzburg', 'Prolific Classical composer'),
('Ludwig van Beethoven', 'German', '1770-12-16', 'Bonn', 'Bridge between Classical and Romantic eras'),
('Niccolò Paganini', 'Italian', '1782-10-27', 'Genoa', 'Virtuoso violinist and composer'),
('Hector Berlioz', 'French', '1803-12-11', 'La Côte-Saint-André', 'Romantic composer and critic'),
('Franz Liszt', 'Hungarian', '1811-10-22', 'Raiding', 'Virtuoso pianist and composer'),
('Robert Schumann', 'German', '1810-06-08', 'Zwickau', 'Romantic composer and critic'),
('Pyotr Ilyich Tchaikovsky', 'Russian', '1840-05-07', 'Votkinsk', 'Romantic composer of ballets and symphonies'),
('Antonín Dvořák', 'Czech', '1841-09-08', 'Nelahozeves', 'Romantic composer inspired by folk music'),
('Edvard Grieg', 'Norwegian', '1843-06-15', 'Bergen', 'Composer inspired by Norwegian folklore'),
('Bedřich Smetana', 'Czech', '1824-03-02', 'Litomyšl', 'Founder of Czech national music'),
('Camille Saint-Saëns', 'French', '1835-10-09', 'Paris', 'Composer, organist and pianist'),
('Gabriel Fauré', 'French', '1845-05-12', 'Pamiers', 'Late Romantic composer and teacher'),
('Jean Sibelius', 'Finnish', '1865-12-08', 'Hämeenlinna', 'National composer of Finland'),
('Richard Strauss', 'German', '1864-06-11', 'Munich', 'Late Romantic and modern composer'),
('Arnold Schoenberg', 'Austrian', '1874-09-13', 'Vienna', 'Pioneer of atonal music'),
('Alban Berg', 'Austrian', '1885-02-09', 'Vienna', 'Member of the Second Viennese School'),
('Anton Webern', 'Austrian', '1883-12-03', 'Vienna', 'Composer of concise modern works'),
('Igor Stravinsky', 'Russian', '1882-06-17', 'Oranienbaum', 'Influential modern composer'),
('Sergei Prokofiev', 'Russian', '1891-04-23', 'Sontsovka', 'Modern composer and pianist'),
('Dmitri Shostakovich', 'Russian', '1906-09-25', 'Saint Petersburg', 'Composer under Soviet regime'),
('Aaron Copland', 'American', '1900-11-14', 'Brooklyn', 'American modern composer'),
('George Gershwin', 'American', '1898-09-26', 'Brooklyn', 'Composer blending jazz and classical'),
('Leonard Bernstein', 'American', '1918-08-25', 'Lawrence', 'Composer, conductor and educator'),
('Philip Glass', 'American', '1937-01-31', 'Baltimore', 'Minimalist composer'),
('Steve Reich', 'American', '1936-10-03', 'New York', 'Minimalist composer'),
('John Cage', 'American', '1912-09-05', 'Los Angeles', 'Avant-garde composer');

