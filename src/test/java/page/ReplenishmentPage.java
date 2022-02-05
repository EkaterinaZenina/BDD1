package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ReplenishmentPage {
    private SelenideElement form = $(".form");

    public static SelenideElement amountField = $("[data-test-id='amount'] input");
    public static SelenideElement fromField = $("[data-test-id='from'] input");

    public static SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public ReplenishmentPage() {
        form.shouldBe(visible);
    }

    public static void transferMoney(DataHelper.CardInfo fromCardInfo, int amountToTransfer) {
        String amount = Integer.toString(amountToTransfer);
        amountField.setValue(amount);
        fromField.setValue(fromCardInfo.getCardNumber());

        transferButton.click();
    }


}
