CREATE TYPE id_row_ot AS OBJECT (
  id           NUMBER
);
/

CREATE TYPE id_nt IS TABLE OF id_row_ot;
/

CREATE OR REPLACE FUNCTION get_id (rows_i IN NUMBER, wait_sec_i IN number) RETURN id_nt PIPELINED AS
BEGIN
  dbms_lock.sleep(wait_sec_i);
  FOR i IN 1 .. rows_i LOOP
    PIPE ROW(id_row_ot(i));
  END LOOP;

  RETURN;
END;
/

--grant execute on get_id to demo;