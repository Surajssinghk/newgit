package Stepdefination;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.login;

public class loginstep {
	login log = new login();

	@Given("enter valide username {string} and password {string}")
	public void enter_valide_username_and_password(String string, String string2) {

		log.enter_valide_username_and_password(string, string2);

	}

	@When("validate click functionality")
	public void validate_click_functionality() {

		log.validate_click_functionality();
	}

}
