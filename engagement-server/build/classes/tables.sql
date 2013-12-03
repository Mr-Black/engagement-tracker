DROP TABLE IF EXISTS Users, EegPower, EegAttention, EegRaw, HeartRate; 

CREATE TABLE IF NOT EXISTS Users (
	google_id VARCHAR(100) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS EegPower (
	id serial PRIMARY KEY,
	google_id VARCHAR(100) references users(google_id),
	timestamp TIMESTAMP WITH TIME ZONE,
	alpha INT,
	beta INT,
	theta INT
);

CREATE TABLE IF NOT EXISTS EegAttention (
	id serial PRIMARY KEY,
	google_id VARCHAR(100) references users(google_id),
	timestamp TIMESTAMP WITH TIME ZONE,
	attention INT
);

CREATE TABLE IF NOT EXISTS EegRaw (
	id serial PRIMARY KEY,
	google_id VARCHAR(100) references users(google_id),
	timestamp TIMESTAMP WITH TIME ZONE,
	raw INT
);

CREATE TABLE IF NOT EXISTS HeartRate (
	id serial PRIMARY KEY,
	google_id VARCHAR(100) references users(google_id),
	timestamp TIMESTAMP WITH TIME ZONE,
	bpm INT
);