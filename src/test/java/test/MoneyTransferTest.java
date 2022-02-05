package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import lombok.var;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();

        var balance1 = dashboardPage.balance1();
        var balance2 = dashboardPage.balance2();

        if (balance1 > balance2) {
            int transferSum = (balance1 - balance2) / 2;
            var cardInfo = DataHelper.getFirstCardInfo();
            var ReplenishmentPage = dashboardPage.secondCard();
            ReplenishmentPage.transferMoney(cardInfo, transferSum);
        }
        if (balance1 < balance2) {
            int transferSum = (balance2 - balance1) / 2;
            var cardInfo = DataHelper.getSecondCardInfo();
            var ReplenishmentPage = dashboardPage.firstCard();
            ReplenishmentPage.transferMoney(cardInfo, transferSum);
        }
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        int amount = 100300;
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.balance1();
        var balanceSecondBefore = dashboardPage.balance2();
        var ReplenishmentPage = dashboardPage.secondCard();
        var cardInfo = DataHelper.getFirstCardInfo();
        ReplenishmentPage.transferMoney(cardInfo, amount);
        int balanceCardsFromAfter= DataHelper.balanceFrom(balanceSecondBefore, amount);
        int balanceCardsToAfter = DataHelper.balanceTo(balanceFirstBefore, amount);
        int balanceFirstAfter = dashboardPage.balance1();
        int balanceSecondAfter = dashboardPage.balance2();

        assertEquals(balanceFirstAfter, balanceCardsFromAfter);
        assertEquals(balanceSecondAfter, balanceCardsToAfter);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        int amount = 110500;
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.balance1();
        var balanceSecondBefore = dashboardPage.balance2();
        var ReplenishmentPage = dashboardPage.firstCard();
        var cardInfo = DataHelper.getSecondCardInfo();
        ReplenishmentPage.transferMoney(cardInfo, amount);
        int balanceAfterFrom = DataHelper.balanceFrom(balanceFirstBefore, amount);
        int balanceAfterTo = DataHelper.balanceTo(balanceSecondBefore, amount);
        int balanceFirstAfter = dashboardPage.balance1();
        int balanceSecondAfter = dashboardPage.balance2();

        assertEquals(balanceFirstAfter, balanceAfterTo);
        assertEquals(balanceSecondAfter, balanceAfterFrom);
    }
}