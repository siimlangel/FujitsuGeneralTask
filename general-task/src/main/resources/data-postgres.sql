INSERT INTO feedback(text, username, email, created_date) VALUES('text1', 'user1', 'user1@gmail.com', NOW());
INSERT INTO feedback(text, username, email, created_date) VALUES('text2', 'user2', 'user2@gmail.com', NOW());
INSERT INTO feedback(text, username, email, created_date) VALUES('text3', 'user3', 'user3@gmail.com', NOW());
INSERT INTO feedback(text, username, email, created_date) VALUES('text4', 'user4', 'user4@gmail.com', NOW());
INSERT INTO feedback(text, username, email, created_date) VALUES('text5', 'user5', 'user5@gmail.com', NOW());

INSERT INTO category(type) VALUES('Patients portal');
INSERT INTO category(type) VALUES('Doctors portal');
INSERT INTO category(type) VALUES('Registration');
INSERT INTO category(type) VALUES('Virtual visit');
INSERT INTO category(type) VALUES('OpenKM');
INSERT INTO category(type) VALUES('Microsoft SharePoint');

INSERT INTO feedback_category(feedback_id, category_id) VALUES(1, 2);
INSERT INTO feedback_category(feedback_id, category_id) VALUES(1, 4);
INSERT INTO feedback_category(feedback_id, category_id) VALUES(2, 5);
INSERT INTO feedback_category(feedback_id, category_id) VALUES(3, 6);
INSERT INTO feedback_category(feedback_id, category_id) VALUES(4, 1);
INSERT INTO feedback_category(feedback_id, category_id) VALUES(4, 5);
INSERT INTO feedback_category(feedback_id, category_id) VALUES(5, 1);