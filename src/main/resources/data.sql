CREATE OR replace PROCEDURE removeStudentGroupById(stg_id bigint )
 language plpgsql AS '

BEGIN
    SET session_replication_role = replica;

DELETE
FROM presence p
WHERE p.student_group_id = stg_id;

DELETE
from student_group sg
WHERE sg.id = stg_id;

SET session_replication_role = DEFAULT;
END;
';