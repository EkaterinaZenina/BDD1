package page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement firstCard = $$("[data-test-id=action-deposit]").first();
    private SelenideElement secondCard = $$("[data-test-id=action-deposit]").last();
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private static SelenideElement balance1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private static SelenideElement balance2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");


    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int balance1() {
        val text = balance1.text();
        return extractBalance(text);
    }
    public int balance2() {
        val text = balance2.text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public ReplenishmentPage firstCard() {
        firstCard.click();
        return new ReplenishmentPage();
    }

    public ReplenishmentPage secondCard() {
        secondCard.click();
        return new ReplenishmentPage();
    }
}
