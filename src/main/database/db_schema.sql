-- #################################################################################################
-- ## Creation of a schema to avoid name clashes                                                  ##
-- #################################################################################################

DROP SCHEMA IF EXISTS poll CASCADE;

-- Create the quiz schema
CREATE SCHEMA poll;
COMMENT ON SCHEMA poll IS 'Schema for containing the objects of the web application';

-- #################################################################################################
-- ## Creation of the tables                                                                      ##
-- #################################################################################################

--
-- This table represents a user
--
-- Version 1.00
CREATE TABLE poll.Link (
    linkid VARCHAR(64) PRIMARY KEY,
    used BOOLEAN DEFAULT 'false' NOT NULL,
	votingid INTEGER NOT NULL,
	notes VARCHAR(64)
);

COMMENT ON TABLE poll.Link IS 'Represents the links for voting';
COMMENT ON COLUMN poll.Link.linkid IS 'The ID of this link';
COMMENT ON COLUMN poll.Link.used IS 'If the link has been used or not';
COMMENT ON COLUMN poll.Link.votingid IS 'The poll who own the link';


--
-- This table represent the polls
--
-- Version 1.00
CREATE TABLE poll.Voting (
    votingid SERIAL PRIMARY KEY,
	question VARCHAR(64) NOT NULL,
	name VARCHAR(64) NOT NULL,
	poll_start timestamp without time zone,
	poll_end timestamp without time zone
);

COMMENT ON TABLE poll.Voting IS 'Represents the polls';
COMMENT ON COLUMN poll.Voting.votingid IS 'The ID of this poll';
COMMENT ON COLUMN poll.Voting.question IS 'The Question of the poll';
COMMENT ON COLUMN poll.Voting.poll_start IS 'Indicates from when answers are acceptable';
COMMENT ON COLUMN poll.Voting.question IS 'The date and the time of poll end';

--
-- This table represent the answers
--
-- Version 1.00
CREATE TABLE poll.Answers (
    answerid SERIAL PRIMARY KEY,
	text VARCHAR(64) NOT NULL,
	votes_received INTEGER DEFAULT 0 NOT NULL,
	votingid INTEGER NOT NULL
);

