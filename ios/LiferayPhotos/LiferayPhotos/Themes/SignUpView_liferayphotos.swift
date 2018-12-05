/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import Foundation
import LiferayScreens

class SignUpView_liferayphotos: SignUpView_default, UITextFieldDelegate {
    
    let blueColor = UIColor(red: 21/255, green: 126/255, blue: 251/255, alpha: 1)
    
    override func onCreated() {
        super.onCreated()
        firstNameField?.delegate = self
        lastNameField?.delegate = self
        emailAddressField?.delegate = self
        passwordField?.delegate = self
    }
    
    override func onShow() {
        super.onShow()
        disableSignUpButton()
    }
    
    @IBAction func textFieldDidChanged(_ sender: UITextField) {
        if oneFieldIsEmpty(){
            disableSignUpButton()
        } else {
            enableSignUpButton()
        }
    }
    
    private func oneFieldIsEmpty() -> Bool {
        return (firstNameField?.text?.isEmpty)!
            || (lastNameField?.text?.isEmpty)!
            || (emailAddressField?.text?.isEmpty)!
            || (passwordField?.text?.isEmpty)!
    }
    
    private func disableSignUpButton() {
        signUpButton?.isEnabled = false
        signUpButton?.backgroundColor = UIColor.white
        signUpButton?.layer.borderWidth = 1
        signUpButton?.layer.borderColor = blueColor.cgColor
        signUpButton?.titleLabel?.textColor = blueColor
        signUpButton?.alpha = 0.5
    }
    
    private func enableSignUpButton() {
        signUpButton?.isEnabled = true
        signUpButton?.alpha = 1
        signUpButton?.layer.borderWidth = 0
        signUpButton?.backgroundColor = blueColor
        signUpButton?.titleLabel?.textColor = UIColor.white
    }
}
