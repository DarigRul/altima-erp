//PRIMERA IMAGEN
function uploadPreview1(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			if (e.target.result.trim().length == 0) {
				$('#serviceImage1')
						.attr('src',
								'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
			} else {
				$('#serviceImage1').attr('src', e.target.result);
			}
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function uploadPreviewExt1(input) {
	if (input.trim().length == 0) {
		var previewSrc = '{{CSS_URL}}/images/placeholder.jpg';
	} else {
		var previewSrc = input;
	}

	$('#serviceImage1').attr('src', previewSrc);
}

function resetUploadPreview1() {
	if (!$('#previousImage1').val()) {
		$('#serviceImage1')
				.attr('src',
						'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
	} else {
		$('#serviceImage1').attr('src',
				'{{PROD_IMG}}/'.$('#previousImage1').val());
	}
}

$("#mainImage1").change(function() {
	uploadPreview1(this);
});

$('[name="mainImgExt1"]').on('change', function() {
	uploadPreviewExt1(this.value);
});

$('#clearUpload1').on('click', function() {
	$('#mainImage1').val('');

	if ($('#imgExt1').val().trim().length == 0) {
		resetUploadPreview1();
	} else {
		uploadPreviewExt1($('#imgExt1').val());
	}
});

$('#clearUploadExt1').on('click', function() {
	$('#imgExt1').val('');

	if (!$('#mainImage1').val()) {
		resetUploadPreview1();
	} else {
		uploadPreview1($('#mainImage1'));
	}
});

// SEGUNDA IMAGEN
function uploadPreview(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			if (e.target.result.trim().length == 0) {
				$('#serviceImage')
						.attr('src',
								'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
			} else {
				$('#serviceImage').attr('src', e.target.result);
			}
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function uploadPreviewExt(input) {
	if (input.trim().length == 0) {
		var previewSrc = '{{CSS_URL}}/images/placeholder.jpg';
	} else {
		var previewSrc = input;
	}

	$('#serviceImage').attr('src', previewSrc);
}

function resetUploadPreview() {
	if (!$('#previousImage').val()) {
		$('#serviceImage')
				.attr('src',
						'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
	} else {
		$('#serviceImage').attr('src',
				'{{PROD_IMG}}/'.$('#previousImage').val());
	}
}

$("#mainImage").change(function() {
	uploadPreview(this);
});

$('[name="mainImgExt"]').on('change', function() {
	uploadPreviewExt(this.value);
});

$('#clearUpload').on('click', function() {
	$('#mainImage').val('');

	if ($('#imgExt').val().trim().length == 0) {
		resetUploadPreview();
	} else {
		uploadPreviewExt($('#imgExt').val());
	}
});

$('#clearUploadExt').on('click', function() {
	$('#imgExt').val('');

	if (!$('#mainImage').val()) {
		resetUploadPreview();
	} else {
		uploadPreview($('#mainImage'));
	}
});

// TELA IMAGEN
function uploadPreviewTela(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			if (e.target.result.trim().length == 0) {
				$('#serviceImageTela')
						.attr('src',
								'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
			} else {
				$('#serviceImageTela').attr('src', e.target.result);
			}
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function uploadPreviewExtTela(input) {
	if (input.trim().length == 0) {
		var previewSrc = '{{CSS_URL}}/images/placeholder.jpg';
	} else {
		var previewSrc = input;
	}

	$('#serviceImageTela').attr('src', previewSrc);
}

function resetUploadPreviewTela() {
	if (!$('#previousImageTela').val()) {
		$('#serviceImageTela')
				.attr('src',
						'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
	} else {
		$('#serviceImageTela').attr('src',
				'{{PROD_IMG}}/'.$('#previousImageTela').val());
	}
}

$("#mainImageTela").change(function() {
	uploadPreviewTela(this);
});

$('[name="mainImgExtTela"]').on('change', function() {
	uploadPreviewExtTela(this.value);
});

$('#clearUploadTela').on('click', function() {
	$('#mainImageTela').val('');

	if ($('#imgExtTela').val().trim().length == 0) {
		resetUploadPreviewTela();
	} else {
		uploadPreviewExtTela($('#imgExt').val());
	}
});

$('#clearUploadExtTela').on('click', function() {
	$('#imgExtTela').val('');

	if (!$('#mainImageTela').val()) {
		resetUploadPreviewTela();
	} else {
		uploadPreviewTela($('#mainImageTela'));
	}
});

// Forro FORRO
function uploadPreviewForro(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			if (e.target.result.trim().length == 0) {
				$('#serviceImageForro')
						.attr('src',
								'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
			} else {
				$('#serviceImageForro').attr('src', e.target.result);
			}
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function uploadPreviewExtForro(input) {
	if (input.trim().length == 0) {
		var previewSrc = '{{CSS_URL}}/images/placeholder.jpg';
	} else {
		var previewSrc = input;
	}

	$('#serviceImageForro').attr('src', previewSrc);
}

function resetUploadPreviewForro() {
	if (!$('#previousImageForro').val()) {
		$('#serviceImageForro')
				.attr('src',
						'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
	} else {
		$('#serviceImageForro').attr('src',
				'{{PROD_IMG}}/'.$('#previousImageForro').val());
	}
}

$("#mainImageForro").change(function() {
	uploadPreviewForro(this);
});

$('[name="mainImgExtForro"]').on('change', function() {
	uploadPreviewExtForro(this.value);
});

$('#clearUploadForro').on('click', function() {
	$('#mainImageForro').val('');

	if ($('#imgExtForro').val().trim().length == 0) {
		resetUploadPreviewForro();
	} else {
		uploadPreviewExtForro($('#imgExt').val());
	}
});

$('#clearUploadExtForro').on('click', function() {
	$('#imgExtForro').val('');

	if (!$('#mainImageForro').val()) {
		resetUploadPreviewForro();
	} else {
		uploadPreviewForro($('#mainImageForro'));
	}
});

// Material

function uploadPreviewMaterial(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			if (e.target.result.trim().length == 0) {
				$('#serviceImageMaterial')
						.attr('src',
								'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
			} else {
				$('#serviceImageMaterial').attr('src', e.target.result);
			}
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function uploadPreviewExtMaterial(input) {
	if (input.trim().length == 0) {
		var previewSrc = '{{CSS_URL}}/images/placeholder.jpg';
	} else {
		var previewSrc = input;
	}

	$('#serviceImageMaterial').attr('src', previewSrc);
}

function resetUploadPreviewMaterial() {
	if (!$('#previousImageMaterial').val()) {
		$('#serviceImageMaterial')
				.attr('src',
						'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
	} else {
		$('#serviceImageMaterial').attr('src',
				'{{PROD_IMG}}/'.$('#previousImageMaterial').val());
	}
}

$("#mainImageMaterial").change(function() {
	uploadPreviewMaterial(this);
});

$('[name="mainImgExtMaterial"]').on('change', function() {
	uploadPreviewExtMaterial(this.value);
});

$('#clearUploadMaterial').on('click', function() {
	$('#mainImageMaterial').val('');

	if ($('#imgExtMaterial').val().trim().length == 0) {
		resetUploadPreviewMaterial();
	} else {
		uploadPreviewExtMaterial($('#imgExt').val());
	}
});

$('#clearUploadExtMaterial').on('click', function() {
	$('#imgExtMaterial').val('');

	if (!$('#mainImageMaterial').val()) {
		resetUploadPreviewMaterial();
	} else {
		uploadPreviewMaterial($('#mainImageMaterial'));
	}
});



//MuestrarioInventario

function uploadPreviewInventario(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			if (e.target.result.trim().length == 0) {
				$('#serviceImageInventario')
						.attr('src',
								'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
			} else {
				$('#serviceImageInventario').attr('src', e.target.result);
			}
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function uploadPreviewExtInventario(input) {
	if (input.trim().length == 0) {
		var previewSrc = '{{CSS_URL}}/images/placeholder.jpg';
	} else {
		var previewSrc = input;
	}

	$('#serviceImageInventario').attr('src', previewSrc);
}

function resetUploadPreviewInventario() {
	if (!$('#previousImageInventario').val()) {
		$('#serviceImageInventario')
				.attr('src',
						'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
	} else {
		$('#serviceImageInventario').attr('src',
				'{{PROD_IMG}}/'.$('#previousImageInventario').val());
	}
}

$("#mainImageInventario").change(function() {
	uploadPreviewInventario(this);
});

$('[name="mainImgExtInventario"]').on('change', function() {
	uploadPreviewExtInventario(this.value);
});

$('#clearUploadInventario').on('click', function() {
	$('#mainImageInventario').val('');

	if ($('#imgExtInventario').val().trim().length == 0) {
		resetUploadPreviewInventario();
	} else {
		uploadPreviewExtInventario($('#imgExt').val());
	}
});

$('#clearUploadExtInventario').on('click', function() {
	$('#imgExtInventario').val('');

	if (!$('#mainImageInventario').val()) {
		resetUploadPreviewInventario();
	} else {
		uploadPreviewInventario($('#mainImageInventario'));
	}
});




// ClienteMoral
function uploadPreviewClienteMoral(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			if (e.target.result.trim().length == 0) {
				$('#serviceImageClienteMoral')
						.attr('src',
								'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
			} else {
				$('#serviceImageClienteMoral').attr('src', e.target.result);
			}
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function uploadPreviewExtClienteMoral(input) {
	if (input.trim().length == 0) {
		var previewSrc = '{{CSS_URL}}/images/placeholder.jpg';
	} else {
		var previewSrc = input;
	}

	$('#serviceImageClienteMoral').attr('src', previewSrc);
}

function resetUploadPreviewClienteMoral() {
	if (!$('#previousImageClienteMoral').val()) {
		$('#serviceImageClienteMoral')
				.attr('src',
						'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
	} else {
		$('#serviceImageClienteMoral').attr('src',
				'{{PROD_IMG}}/'.$('#previousImageClienteMoral').val());
	}
}

$("#mainImageClienteMoral").change(function() {
	uploadPreviewClienteMoral(this);
});

$('[name="mainImgExtClienteMoral"]').on('change', function() {
	uploadPreviewExtClienteMoral(this.value);
});

$('#clearUploadClienteMoral').on('click', function() {
	$('#mainImageClienteMoral').val('');

	if ($('#imgExtClienteMoral').val().trim().length == 0) {
		resetUploadPreviewClienteMoral();
	} else {
		uploadPreviewExtClienteMoral($('#imgExt').val());
	}
});

$('#clearUploadExtClienteMoral').on('click', function() {
	$('#imgExtClienteMoral').val('');

	if (!$('#mainImageClienteMoral').val()) {
		resetUploadPreviewClienteMoral();
	} else {
		uploadPreviewClienteMoral($('#mainImageClienteMoral'));
	}
});


//ClienteFisica
function uploadPreviewClienteFisica(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			if (e.target.result.trim().length == 0) {
				$('#serviceImageClienteFisica')
						.attr('src',
								'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
			} else {
				$('#serviceImageClienteFisica').attr('src', e.target.result);
			}
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function uploadPreviewExtClienteFisica(input) {
	if (input.trim().length == 0) {
		var previewSrc = '{{CSS_URL}}/images/placeholder.jpg';
	} else {
		var previewSrc = input;
	}

	$('#serviceImageClienteFisica').attr('src', previewSrc);
}

function resetUploadPreviewClienteFisica() {
	if (!$('#previousImageClienteFisica').val()) {
		$('#serviceImageClienteFisica')
				.attr('src',
						'http://catawbabrewing.com/wp-content/themes/catawba/images/placeholder.png');
	} else {
		$('#serviceImageClienteFisica').attr('src',
				'{{PROD_IMG}}/'.$('#previousImageClienteFisica').val());
	}
}

$("#mainImageClienteFisica").change(function() {
	uploadPreviewClienteFisica(this);
});

$('[name="mainImgExtClienteFisica"]').on('change', function() {
	uploadPreviewExtClienteFisica(this.value);
});

$('#clearUploadClienteFisica').on('click', function() {
	$('#mainImageClienteFisica').val('');

	if ($('#imgExtClienteFisica').val().trim().length == 0) {
		resetUploadPreviewClienteFisica();
	} else {
		uploadPreviewExtClienteFisica($('#imgExt').val());
	}
});

$('#clearUploadExtClienteFisica').on('click', function() {
	$('#imgExtClienteFisica').val('');

	if (!$('#mainImageClienteFisica').val()) {
		resetUploadPreviewClienteFisica();
	} else {
		uploadPreviewClienteFisica($('#mainImageClienteFisica'));
	}
});
				