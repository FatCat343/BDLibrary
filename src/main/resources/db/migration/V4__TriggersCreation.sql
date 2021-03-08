CREATE OR REPLACE FUNCTION change_dist_on_delete_reader() RETURNS trigger AS
$$BEGIN
    UPDATE library_schema.distribution SET reader_id = 0
    WHERE reader_id = OLD.reader_id;
    RETURN OLD;
END;$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_reader
    BEFORE DELETE ON library_schema.reader FOR EACH ROW
EXECUTE PROCEDURE change_dist_on_delete_reader();
--
-- CREATE OR REPLACE FUNCTION del_reader_on_delete_student() RETURNS trigger AS
-- $$BEGIN
--     DELETE FROM library_schema.reader
--     WHERE reader_id = OLD.reader_id;
--     RETURN OLD;
-- END;$$ LANGUAGE plpgsql;
--
-- CREATE TRIGGER delete_student
--     BEFORE DELETE ON library_schema.student FOR EACH ROW
-- EXECUTE PROCEDURE del_reader_on_delete_student();