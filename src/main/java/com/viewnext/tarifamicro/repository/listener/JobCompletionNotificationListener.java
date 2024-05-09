package com.viewnext.tarifamicro.repository.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

	private final JdbcTemplate jdbcTemplate;

	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Cuando acaba el job muestra un mensaje sobre si lo ha completado o no
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("TIRA");
		} else {
			log.info("No tira");
		}
	}

}
