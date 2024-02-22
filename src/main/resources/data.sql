INSERT INTO User (userName, email, password, postCount, followingCount, followerCount, introduction, occupation, birthday, city) VALUES
('John Doe', 'john.doe@example.com', 'password1', 5, 10, 15, 'A tech enthusiast.', 'Software Developer', '1990-01-01', 'New York'),
('Jane Smith', 'jane.smith@example.com', 'password2', 8, 20, 25, 'Avid reader and writer.', 'Content Writer', '1992-02-02', 'Los Angeles'),
('Bob Brown', 'boba.brown@example.com', 'password3', 10, 30, 5, 'Music lover.', 'Musician', '1988-03-03', 'Chicago'),
('Bobby Brown', 'bodb.brown@example.com', 'password3', 10, 30, 5, 'Music lover.', 'Musician', '1988-03-03', 'Chicago'),
('Bobdd Brown', 'bobdd.brown@example.com', 'password3', 10, 30, 5, 'Music lover.', 'Musician', '1988-03-03', 'Chicago');

INSERT INTO Post (userId, content, likeCount, commentCount) VALUES
(1, 'Hello, world!', 100, 10),
(2, 'Exploring the beauty of nature.', 150, 20),
(3, 'The journey of a thousand miles begins with one step.', 200, 30),
(4, 'The journey of a thousand miles begins with one step.', 200, 30),
(5, 'The journey of a thousand miles begins with one step.', 200, 30);

INSERT INTO Comment (postId, userId, comment) VALUES
(1, 2, 'Great post!'),
(2, 1, 'Absolutely beautiful!'),
(3, 2, 'Very inspirational.');


INSERT INTO postLike (userId, postId) VALUES
(1, 2),
(2, 1),
(3, 1);

INSERT INTO userFollow (followerId, followingId) VALUES
(1, 2),
(2, 3),
(3, 1);

INSERT INTO Tag (tagName) VALUES
('Nature'),
('Inspiration'),
('Technology');

INSERT INTO PostTag (postId, tagId) VALUES
(1, 3),
(2, 1),
(3, 2);