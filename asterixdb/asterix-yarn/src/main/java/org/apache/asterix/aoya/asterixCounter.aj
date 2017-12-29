aspect Logging {
    Integer counter = 0;
    
    pointcut count():
        call(* AsterixApplicationMaster.AsterixApplicationMaster(*));

    before(): count() {
        counter++;
        System.out.println(counter);
    }
}
