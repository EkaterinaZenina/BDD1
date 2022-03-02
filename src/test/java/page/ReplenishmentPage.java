package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ReplenishmentPage {
    private SelenideElement form = $(".form");
    private  SelenideElement amountField = $("[data-test-id='amount'] input");
    private  SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");



    public void transferMoney(DataHelper.CardInfo fromCardInfo, int amountToTransfer) {
        String amount = Integer.toString(amountToTransfer);
        amountField.sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        amountField.setValue(amount);
        fromField.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        fromField.setValue(fromCardInfo.getCardNumber());
        transferButton.click();


    }
}
