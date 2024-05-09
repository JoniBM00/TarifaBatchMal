package com.viewnext.tarifamicro.repository.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.viewnext.tarifamicro.business.model.Tarifa;
import com.viewnext.tarifamicro.repository.listener.JobCompletionNotificationListener;

@Configuration
public class BatchConfig {

	/**
	 * Hace un job
	 * 
	 * @param jobRepository
	 * @param step1
	 * @param listener
	 * @return Retorna un Job
	 */
	@Bean
	public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
		return new JobBuilder("importUserJob", jobRepository).listener(listener).start(step1).build();
	}

	/**
	 * Hace un Step que lee las tarifas de la bbdd y escribe en csv
	 * 
	 * @param jobRepository
	 * @param transactionManager
	 * @param reader
	 * @param writer
	 * @return retorna una Step
	 */
	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			JdbcCursorItemReader<Tarifa> reader, FlatFileItemWriter<Tarifa> writer) {
		return new StepBuilder("step1", jobRepository).<Tarifa, Tarifa>chunk(10, transactionManager).reader(reader)
				.writer(writer).allowStartIfComplete(true).build();
	}

}
