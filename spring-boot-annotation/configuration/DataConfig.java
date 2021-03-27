@Configuration
public class DataConfig {
     
    @Bean
    public DataUtils dataUtils()
    {
        return new DataUnits();
    }
}
