DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS postLike;
DROP TABLE IF EXISTS userFollow;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS postTag;

CREATE TABLE user (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    userName VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    postCount INT DEFAULT 0,
    followingCount INT DEFAULT 0,
    followerCount INT DEFAULT 0,
    introduction TEXT,
    occupation VARCHAR(255),
    birthday DATE,
    city VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE post (
    postId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    content TEXT,
    likeCount INT DEFAULT 0,
    commentCount INT DEFAULT 0,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comment (
     commentId INT PRIMARY KEY AUTO_INCREMENT,
     postId INT,
     userId INT,
     comment TEXT,
     createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE postLike (
    userId INT,
    postId INT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (userId, postId)
);

CREATE TABLE userFollow (
    followerId INT,
    followingId INT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (followerId, followingId)
);

CREATE TABLE tag (
     tagId INT PRIMARY KEY AUTO_INCREMENT,
     tagName VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE postTag (
     postId INT,
     tagId INT,
     PRIMARY KEY (postId, tagId)
);