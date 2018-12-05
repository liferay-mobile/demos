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

import UIKit
import LiferayScreens

class LoginViewController: XibViewController, LoginScreenletDelegate {

    @IBOutlet weak var screenlet: LoginScreenlet!

    override func viewDidLoad() {
        super.viewDidLoad()
        screenlet.delegate = self
        screenlet.saveCredentials = true
        screenlet.presentingViewController = self
    }
    
    func screenlet(_ screenlet: BaseScreenlet, onLoginResponseUserAttributes attributes: [String : AnyObject]) {
        let vc = MainViewController()

		let nav = UINavigationController(rootViewController: vc)
        self.present(nav, animated: true, completion: nil)
    }
    
    func screenlet(_ screenlet: BaseScreenlet, onLoginError error: NSError) {
        showAlert(title: "error")
    }
    
    func screenlet(_ screenlet: BaseScreenlet, onCredentialsSavedUserAttributes attributes: [String : AnyObject]) {
        showAlert(title: "credentials saved")
    }
    
    func screenlet(_ screenlet: LoginScreenlet, onCredentialsLoadedUserAttributes attributes: [String : AnyObject]) {
        showAlert(title: "credentials saved")
    }
}
