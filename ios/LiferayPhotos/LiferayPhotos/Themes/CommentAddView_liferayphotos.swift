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

class CommentAddView_liferayphotos: CommentAddView_default {
    
    
    @IBOutlet weak var userPortraitScreenlet: UserPortraitScreenlet!
    
    override func onShow() {
        super.onShow()
        userPortraitScreenlet.load(userId: (SessionContext.currentContext?.user.userId)!)
    }
    
    
    override func onCreated() {
        super.onCreated()
        layer.borderWidth = 1
        let alpha = CGFloat(0.3)
        let borderColor =  UIColor.gray
        layer.borderColor = borderColor.withAlphaComponent(alpha).cgColor
        
        sendCommentButton?.backgroundColor = nil
    }
    
    override func updateButton() {
        self.sendCommentButton?.isEnabled = !(addCommentTextField?.text?.isEmpty ?? false)

        if let sendCommentButton = sendCommentButton {
            sendCommentButton.tintColor =
                sendCommentButton.isEnabled ? UIColor.blue :
                UIColor.blue.withAlphaComponent(0.3)
        }
    }
}
