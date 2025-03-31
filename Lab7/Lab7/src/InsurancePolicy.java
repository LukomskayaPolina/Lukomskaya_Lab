class InsurancePolicy extends CardDecorator {
    public InsurancePolicy(UniversalCard card) {
        super(card);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Функция: Страховой полис");
    }
}