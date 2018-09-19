package com.shawtravel.booking.constants;

public interface SqlQuery {
	String TICKET_DETAILS = "select isoverdue,est_duedate,created,updated,(select ost_ticket_priority.priority from ost_ticket__cdata,ost_ticket_priority where ost_ticket__cdata.ticket_id=ost_ticket.ticket_id and ost_ticket__cdata.priority=ost_ticket_priority.priority_id) as value,(select distinct number from ost_thread_entry where ost_thread_entry.thread_id = ost_ticket.ticket_id)as number,(select body from ost_thread_entry where ost_thread_entry.thread_id = ost_ticket.ticket_id limit 1 ) as body,(select poster from ost_thread_entry where ost_thread_entry.thread_id = ost_ticket.ticket_id limit 1 ) as poster from ost_ticket where status_id=1";
	String OPEN_OS_TICKET_COUNT= "SELECT count(1) FROM ost_ticket WHERE status_id=1";
	String UPDATED_THREAD_IDS = "SELECT thread_id FROM ost_thread_entry GROUP BY thread_id HAVING COUNT(*) > 1";
	String TIME_DIFFERENCE = "SELECT TIMESTAMPDIFF(HOUR,min(created),max(created)) FROM ost_thread_entry WHERE thread_id=? order by thread_id asc limit 2";
	String SLA_BREACH = "SELECT count(*) from ost_ticket where isoverdue='1'";
	String SLA_NON_BREACH = "SELECT count(*) from ost_ticket where isoverdue='0'";
}
