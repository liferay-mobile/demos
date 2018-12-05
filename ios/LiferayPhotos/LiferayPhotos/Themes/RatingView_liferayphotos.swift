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

import LiferayScreens

class RatingView_liferayphotos: BaseScreenletView, RatingViewModel {

	@IBOutlet weak var likeImage: UIImageView?
	@IBOutlet weak var likesLabel: UILabel?

	open var defaultRatingsGroupCount: Int32 = 2

	override var screenlet: BaseScreenlet? {
		didSet {
			let gesture = UITapGestureRecognizer(target: self, action: #selector(handleTap))

			self.screenlet?.addGestureRecognizer(gesture)
		}
	}

	var ratingEntry: RatingEntry? {
		didSet {
			likesLabel?.text = "\(ratingEntry?.totalCount ?? 0) likes"

			if ratingEntry?.userScore == -1 {
				likeImage?.image = UIImage(named: "like_disabled")
			}
			else {
				likeImage?.image = UIImage(named: "like_enabled")
			}
		}
	}

	func handleTap(_ recognizer: UIGestureRecognizer) {
		if ratingEntry?.userScore == -1 {
			self.userAction(name: RatingScreenlet.UpdateRatingAction, sender: 1)
		}
		else {
			self.userAction(name: RatingScreenlet.DeleteRatingAction)
		}
	}

	override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
		super.touchesBegan(touches, with: event)
	}

}
