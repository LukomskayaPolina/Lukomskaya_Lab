class Passport extends CardDecorator {
    public Passport(UniversalCard card) {
        super(card);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Функция: Паспорт");
    }
}
