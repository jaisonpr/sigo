package br.com.indtexbr.backend.gestaoprocessos.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AtualizacaoNormaTask {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


	@Scheduled(fixedDelay = 2000)
	public void scheduleTaskWithFixedRate() {

	    log.info("Fixed Delay Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	    try {
	        TimeUnit.SECONDS.sleep(5);
	    } catch (InterruptedException ex) {
	        log.error("Ran into an error {}", ex);
	        throw new IllegalStateException(ex);
	    }
	}
}
