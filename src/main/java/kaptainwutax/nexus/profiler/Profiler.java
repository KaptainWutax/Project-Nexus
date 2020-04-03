package kaptainwutax.nexus.profiler;

import java.util.LinkedHashMap;
import java.util.Map;

public class Profiler {

	public static final Profiler GLOBAL = new Profiler();

	protected String currentStep;
	protected long startTime;

	protected Map<String, Long> profile = new LinkedHashMap<>();
	protected long totalTime = 0L;

	public void end() {
		if(this.currentStep == null) {
			throw new IllegalStateException("Cannot end a null step");
		}

		long currentTime = this.profile.getOrDefault(this.currentStep, 0L);
		long increment = System.nanoTime() - this.startTime;
		currentTime += increment;
		this.totalTime += increment;
		this.profile.put(this.currentStep, currentTime);

		this.currentStep = null;
		this.startTime = 0;
	}

	public void start(String step) {
		if(this.currentStep != null) {
			throw new IllegalStateException("Step [" + this.currentStep + "] is running");
		}

		this.currentStep = step;
		this.startTime = System.nanoTime();
	}

	public void swap(String step) {
		this.end();
		this.start(step);
	}

	public long getTime(String step) {
		return this.profile.getOrDefault(step, 0L);
	}

	public void printReport() {
		System.out.format("============================================================\n");

		this.profile.entrySet().stream().sorted((e1, e2) -> (int)Math.signum(e2.getValue() - e1.getValue())).forEach(e -> {
			System.out.format(e.getKey() + " took %d microseconds. %f%%\n", e.getValue() / 1000, e.getValue() * 100.0F / this.totalTime);
		});

		System.out.format("Profiling finished in %d microseconds.\n", this.totalTime / 1000);
		System.out.format("============================================================\n");
	}

	public void clear() {
		if(this.currentStep != null)this.end();
		this.profile.clear();
		this.totalTime = 0;
	}

}
