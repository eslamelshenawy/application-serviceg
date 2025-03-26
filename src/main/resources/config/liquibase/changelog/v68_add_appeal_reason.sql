ALTER TABLE application.appeal_request ALTER COLUMN appeal_reason TYPE varchar USING appeal_reason::varchar;



ALTER TABLE application.appeal_request ALTER COLUMN appeal_committee_decision TYPE varchar USING appeal_committee_decision::varchar;



ALTER TABLE application.appeal_request ALTER COLUMN appeal_committee_decision_comment  TYPE varchar USING appeal_committee_decision::varchar;









ALTER TABLE application.opposition ALTER COLUMN applicant_examiner_notes TYPE varchar USING applicant_examiner_notes::varchar;