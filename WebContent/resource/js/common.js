console.clear();

function MobileSideBar__init() {
	$('.mobile-top-bar .btn-toggle-mobile-side-bar').click(function() {
		var $this = $(this);

		if ($this.hasClass('active')) {
			$this.removeClass('active');
			$('.mobile-side-bar').removeClass('active');
		} else {
			$this.addClass('active')
			$('.mobile-side-bar').addClass('active');
		}
	});
}