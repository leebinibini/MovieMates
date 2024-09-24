// Table of contents
/**** Sidebar ****/
/**** List product ****/
/**** Handle layout cols in list product ****/
/**** Filer product by type(in breadcrumb and sidebar) ****/
/**** Tow bar filter product by price ****/
/**** Function to fetch products from JSON file ****/
/*****----- handle event when user change filter -----************/
/*****----- Filter options -----************/
/*****----- filter product base on items filtered -----************/
/*****----- Handle sort product -----************/
/*****----- Rerender product base on items filtered -----************/
/*****----- filter product -----************/
/*****----- sort product -----************/
/*****----- handle events when user change filter -----************/
/**** Function to render products for a specific page ****/
/**** Function to render pagination buttons ****/
/**** Initial fetch of products ****/



// Sidebar
const filterSidebarBtn = document.querySelector('.filter-sidebar-btn')
const sidebar = document.querySelector('.sidebar')
const sidebarMain = document.querySelector('.sidebar .sidebar-main')
const closeSidebarBtn = document.querySelector('.sidebar .sidebar-main .close-sidebar-btn')

if (filterSidebarBtn && sidebar) {
    filterSidebarBtn.addEventListener('click', () => {
        sidebar.classList.toggle('open')
    })

    if (sidebarMain) {
        sidebar.addEventListener('click', () => {
            sidebar.classList.remove('open')
        })

        sidebarMain.addEventListener('click', (e) => {
            e.stopPropagation()
        })

        closeSidebarBtn.addEventListener('click', () => {
            sidebar.classList.remove('open')
        })
    }
}


// List product
const productContainer = document.querySelector('.shop-product .list-product-block');
const productList = document.querySelector('.list-product-block .list-product');
const listPagination = document.querySelector('.list-pagination');

let currentPage = 1;
let productsPerPage = productList ? Number(productList.getAttribute('data-item')) : 12;
let productsData = [];


// Filer product by type(in breadcrumb and sidebar)
let selectedType = localStorage.getItem('selectedType');
localStorage.setItem('selectedType', '')


// Tow bar filter product by price
const rangeInput = document.querySelectorAll('.range-input input')
const progress = document.querySelector('.tow-bar-block .progress')
const minPrice = document.querySelector('.min-price')
const maxPrice = document.querySelector('.max-price')

let priceGap = 10

rangeInput.forEach(input => {
    input.addEventListener('input', e => {
        let minValue = parseInt(rangeInput[0].value)
        let maxValue = parseInt(rangeInput[1].value)

        if (maxValue - minValue < priceGap) {
            if (e.target.class === 'range-min') {
                rangeInput[0].value = maxValue - priceGap
            } else {
                rangeInput[1].value = minValue + priceGap
            }
        } else {
            progress.style.left = (minValue / rangeInput[0].max) * 100 + "%";
            progress.style.right = 100 - (maxValue / rangeInput[1].max) * 100 + "%";
        }

        minPrice.innerHTML = '$' + minValue
        maxPrice.innerHTML = '$' + maxValue

        if (minValue >= 290) {
            minPrice.innerHTML = '$' + 290
        }

        if (maxValue <= 10) {
            maxPrice.innerHTML = '$' + 10
        }
    })
})


// Function to fetch products from JSON file
function fetchProducts() {
    fetch('./assets/data/Product.json')
        .then(response => response.json())
        .then(data => {
            productsData = data;
            renderProducts(currentPage, productsData);
            renderPagination(productsData);

            // Switch between grid <-> list layout
            const layoutItems = productContainer.querySelectorAll('.choose-layout .item')

            layoutItems.forEach(item => {
                item.addEventListener('click', (e) => {
                    e.stopPropagation();
                    if (item.classList.contains('style-grid')) {
                        productContainer.classList.remove('style-list')
                        productContainer.classList.add('style-grid')
                        productContainer.querySelector('.list-product').classList.remove('flex', 'flex-col')
                        productContainer.querySelector('.list-product').classList.add('grid')
                        productContainer.querySelector('.list-product').setAttribute('data-item', '9')
                        productsPerPage = 9
                    }
                    else if (item.classList.contains('style-list')) {
                        productContainer.classList.remove('style-grid')
                        productContainer.classList.add('style-list')
                        productContainer.querySelector('.list-product').classList.remove('grid')
                        productContainer.querySelector('.list-product').classList.add('flex', 'flex-col')
                        productContainer.querySelector('.list-product').setAttribute('data-item', '4')
                        productsPerPage = 4
                    }
                    renderProducts(1, productsData);
                    currentPage = 1;
                    renderPagination(productsData)
                    addEventToProductItem(productsData)
                })
            })

            let selectedFilters = {};

            // handle event when user change filter
            function handleFiltersChange() {
                selectedFilters = {
                    type: document.querySelector('.filter-type .active')?.getAttribute('data-item'),
                    size: Array.from(document.querySelectorAll('.filter-size .size-item.active')).map(item => item.getAttribute('data-item')),
                    color: Array.from(document.querySelectorAll('.filter-color .color-item.active')).map(item => item.getAttribute('data-item')),
                    brand: Array.from(document.querySelectorAll('.filter-brand .brand-item input[type="checkbox"]:checked')).map(item => item.getAttribute('name')),
                    minPrice: 0, //default
                    maxPrice: 300, //default
                    sale: document.querySelector('.check-sale input[type="checkbox"]:checked')
                };

                // Filter options
                if (document.querySelector('.filter-type select')) {
                    const typeValue = document.querySelector('.filter-type select').value;
                    selectedFilters.type = typeValue !== "null" ? typeValue : [];
                }

                if (document.querySelector('.filter-size select')) {
                    const sizeValue = document.querySelector('.filter-size select').value;
                    selectedFilters.size = sizeValue !== "null" ? sizeValue : [];
                }

                if (document.querySelector('.filter-color select')) {
                    const colorValue = document.querySelector('.filter-color select').value;
                    selectedFilters.color = colorValue !== "null" ? colorValue : [];
                }

                if (document.querySelector('.filter-brand select')) {
                    const brandValue = document.querySelector('.filter-brand select').value;
                    selectedFilters.brand = brandValue !== "null" ? brandValue : [];
                }

                if (rangeInput && rangeInput.length > 1) {
                    selectedFilters.minPrice = parseInt(rangeInput[0].value);
                    selectedFilters.maxPrice = parseInt(rangeInput[1].value);

                    if (document.querySelector('.filter-price select')) {
                        const selectPrice = document.querySelector('.filter-price select').value;
                        if (selectPrice !== "null") {
                            const [min, max] = selectPrice.split('-').map(val => parseInt(val.replace('$', '').trim()));
                            selectedFilters.minPrice = parseInt(min);
                            selectedFilters.maxPrice = parseInt(max);
                        } else {
                            selectedFilters.minPrice = 0;
                            selectedFilters.maxPrice = 300;
                        }
                    }
                }

                // filter product base on items filtered
                let filteredProducts = productsData.filter(product => {
                    if (selectedFilters.type && selectedFilters.type?.length > 0 && product.type !== selectedFilters.type) return false;
                    if (selectedFilters.size && selectedFilters.size?.length > 0 && !product.sizes.some(size => selectedFilters.size.includes(size))) return false;
                    if (selectedFilters.color && selectedFilters.color?.length > 0 && !product.variation.some(variant => selectedFilters.color.includes(variant.color))) return false;
                    if (selectedFilters.brand && selectedFilters.brand?.length > 0 && !selectedFilters.brand.includes(product.brand)) return false;
                    if (selectedFilters.minPrice && product.price < selectedFilters.minPrice) return false;
                    if (selectedFilters.maxPrice && product.price > selectedFilters.maxPrice) return false;
                    if (selectedFilters.sale && product.sale !== true) return false;
                    return true;
                });

                
                // Set list filtered
                const listFiltered = document.querySelector('.list-filtered')

                let newHtmlListFiltered = `
                    <div class="total-product">
                        ${filteredProducts?.length}
                        <span class='text-secondary pl-1'>Products Found</span>
                    </div>
                    <div class="list flex items-center gap-3">
                        <div class='w-px h-4 bg-line'></div>
                        ${selectedFilters.type?.length ? (
                        `
                                <div class="item flex items-center px-2 py-1 gap-1 bg-linear rounded-full capitalize" data-type="type">
                                    <i class='ph ph-x cursor-pointer'></i>
                                    <span>${selectedFilters.type}</span>
                                </div>
                            `
                    ) : ''}
                        ${selectedFilters.size?.length ? (
                        `<div class="item flex items-center px-2 py-1 gap-1 bg-linear rounded-full capitalize" data-type="size">
                                <i class='ph ph-x cursor-pointer'></i>
                                <span>${selectedFilters.size}</span>
                            </div>`
                    ) : ''}
                        ${selectedFilters.color?.length ? (
                        `<div class="item flex items-center px-2 py-1 gap-1 bg-linear rounded-full capitalize" data-type="color">
                                <i class='ph ph-x cursor-pointer'></i>
                                <span>${selectedFilters.color}</span>
                            </div>`
                    ) : ''}
                        ${typeof selectedFilters.brand === 'object' && selectedFilters.brand?.length ? (
                        `
                            ${selectedFilters.brand.map(item => (
                            `
                                    <div class="item flex items-center px-2 py-1 gap-1 bg-linear rounded-full capitalize" data-type="brand" data-item=${item}>
                                        <i class='ph ph-x cursor-pointer'></i>
                                        <span>${item}</span>
                                    </div>
                                `
                        )).join('')}
                        `
                    ) : ''}
                        ${typeof selectedFilters.brand !== 'object' && selectedFilters.brand?.length ? (
                        `
                                <div class="item flex items-center px-2 py-1 gap-1 bg-linear rounded-full capitalize" data-type="brand" data-item=${selectedFilters.brand}>
                                    <i class='ph ph-x cursor-pointer'></i>
                                    <span>${selectedFilters.brand}</span>
                                </div>
                            `
                    ) : ''}
                    </div>
                    <div
                        class="clear-btn flex items-center px-2 py-1 gap-1 rounded-full w-fit border border-red cursor-pointer">
                        <i class='ph ph-x cursor-pointer text-red'></i>
                        <span class='text-button-uppercase text-red'>Clear All</span>
                    </div>
                `

                // remove content in listFiltered
                listFiltered.innerHTML = '';

                // add newHtmlListFiltered to listFiltered
                listFiltered.insertAdjacentHTML('beforeend', newHtmlListFiltered);

                // Remove filtered
                // Remove item from list filtered
                const clearBtnItem = document.querySelectorAll('.list-filtered .list .item')

                clearBtnItem.forEach(btn => {
                    btn.addEventListener('click', () => {
                        let dataType = btn.getAttribute('data-type')
                        document.querySelectorAll(`.filter-${dataType} .active`)?.forEach(item => item.classList.remove('active'))
                        if (document.querySelector(`.filter-${dataType} select`)) document.querySelector(`.filter-${dataType} select`).value = null

                        if (dataType === 'brand') {
                            let dataItem = btn.getAttribute('data-item')
                            document.querySelectorAll('.filter-brand .brand-item input[type="checkbox"]:checked').forEach(item => {
                                if (item.id === dataItem) {
                                    item.checked = false
                                }
                            })
                        }

                        handleFiltersChange()
                        console.log(selectedFilters.type, selectedFilters.size, selectedFilters.color, selectedFilters.brand);
                        if (!selectedFilters.type && selectedFilters.size?.length === 0 && selectedFilters.color?.length === 0 && selectedFilters.brand?.length === 0) {
                            listFiltered.innerHTML = ''
                        }
                    })
                })

                // Remove all
                const clearBtn = document.querySelector('.list-filtered .clear-btn')

                clearBtn?.addEventListener('click', () => {
                    document.querySelectorAll('.filter-type .active')?.forEach(item => item.classList.remove('active'))
                    document.querySelectorAll('.filter-size .active')?.forEach(item => item.classList.remove('active'))
                    document.querySelectorAll('.filter-color .active')?.forEach(item => item.classList.remove('active'))
                    document.querySelectorAll('.filter-brand .brand-item input[type="checkbox"]:checked').forEach(item => item.checked = false)
                    if (document.querySelector('.check-sale input[type="checkbox"]:checked')) {
                        document.querySelector('.check-sale input[type="checkbox"]:checked').checked = false
                    }

                    handleFiltersChange()
                    listFiltered.innerHTML = ''
                })

                // Handle sort product
                if (sortOption === 'soldQuantityHighToLow') {
                    filteredProducts = filteredProducts.sort((a, b) => b.sold - a.sold)
                }

                if (sortOption === 'discountHighToLow') {
                    filteredProducts = filteredProducts
                        .sort((a, b) => (
                            (Math.floor(100 - ((b.price / b.originPrice) * 100))) - (Math.floor(100 - ((a.price / a.originPrice) * 100)))
                        ))
                }

                if (sortOption === 'priceHighToLow') {
                    filteredProducts = filteredProducts.sort((a, b) => b.price - a.price)
                }

                if (sortOption === 'priceLowToHigh') {
                    filteredProducts = filteredProducts.sort((a, b) => a.price - b.price)
                }

                // Rerender product base on items filtered
                renderProducts(1, filteredProducts);
                currentPage = 1;
                renderPagination(filteredProducts)
                addEventToProductItem(productsData)
            }

            // filter product
            const typeItems = document.querySelectorAll('.filter-type .item')
            const sizeItems = document.querySelectorAll('.filter-size .size-item')
            const colorItems = document.querySelectorAll('.filter-color .color-item')
            const brandItems = document.querySelectorAll('.filter-brand .brand-item')
            const checkboxBrandItems = document.querySelectorAll('.filter-brand .brand-item input[type="checkbox"]')
            const checkSale = document.querySelector('.check-sale input')

            // sort product
            const sortSelect = document.querySelector('.sort-product select')
            let sortOption = sortSelect.value

            // Get filter type from url
            const pathname = new URL(window.location.href)
            const typeUrl = pathname.searchParams.get('type') === null ? '' : pathname.searchParams.get('type')

            if (typeUrl !== '') {
                localStorage.setItem('selectedType', typeUrl)
                typeItems.forEach(item => {
                    if (item.getAttribute('data-item') === localStorage.getItem('selectedType')) {
                        item.classList.add('active')
                    } else {
                        item.classList.remove('active')
                    }

                    handleFiltersChange();
                });
            }

            // handle events when user change filter
            typeItems.forEach(item => {
                item.addEventListener('click', () => {
                    localStorage.setItem('selectedType', item.getAttribute('data-item'))

                    typeItems.forEach(item => {
                        if (item.getAttribute('data-item') === localStorage.getItem('selectedType')) {
                            item.classList.add('active')
                        } else {
                            item.classList.remove('active')
                        }
                    })
                    handleFiltersChange();
                });

                if (item.querySelector('.number')) {
                    item.querySelector('.number').innerHTML = productsData.filter(product => product.type === item.getAttribute('data-item')).length
                }
            });

            // shop-filter-options.html
            if (document.querySelector('.filter-type select')) {
                document.querySelector('.filter-type select').addEventListener('change', handleFiltersChange)
            }

            sizeItems.forEach(item => {
                item.addEventListener('click', () => {
                    let parent = item.parentElement;
                    if (!parent.querySelector(".active")) {
                        item.classList.add("active");
                    } else {
                        parent.querySelector(".active").classList.remove("active");
                        item.classList.add("active");
                    }
                    handleFiltersChange()
                });
            });

            // shop-filter-options.html
            if (document.querySelector('.filter-size select')) {
                document.querySelector('.filter-size select').addEventListener('change', handleFiltersChange)
            }

            colorItems.forEach(item => {
                item.addEventListener('click', () => {
                    let parent = item.parentElement;
                    if (!parent.querySelector(".active")) {
                        item.classList.add("active");
                    } else {
                        parent.querySelector(".active").classList.remove("active");
                        item.classList.add("active");
                    }
                    handleFiltersChange()
                });
            });

            // shop-filter-options.html
            if (document.querySelector('.filter-color select')) {
                document.querySelector('.filter-color select').addEventListener('change', handleFiltersChange)
            }

            brandItems.forEach(item => {
                if (item.querySelector('.number')) {
                    item.querySelector('.number').innerHTML = productsData.filter(product => product.brand === item.getAttribute('data-item')).length
                }
            })

            checkboxBrandItems.forEach(item => {
                item.addEventListener('change', handleFiltersChange);
            })

            // shop-filter-options.html
            if (document.querySelector('.filter-brand select')) {
                document.querySelector('.filter-brand select').addEventListener('change', handleFiltersChange)
            }

            rangeInput.forEach(input => {
                input.addEventListener('input', handleFiltersChange)
            })

            // shop-filter-options.html
            if (document.querySelector('.filter-price select')) {
                document.querySelector('.filter-price select').addEventListener('change', handleFiltersChange)
            }

            if (checkSale) {
                checkSale.addEventListener('change', handleFiltersChange)
            }

            sortSelect.addEventListener('change', () => {
                sortOption = sortSelect.value
                handleFiltersChange();
            })
        })
        .catch(error => console.error('Error fetching products:', error));
}

// Function to render products for a specific page
function renderProducts(page, products = []) {
    productList.innerHTML = '';
    const productsToDisplay = products;

    const startIndex = (page - 1) * productsPerPage;
    const endIndex = startIndex + productsPerPage;
    const displayedProducts = productsToDisplay.slice(startIndex, endIndex);

    if (displayedProducts.length === 0) {
        productList.innerHTML = `
            <div class="list-empty">
                <p class="text-gray-500 text-base">No product found</p>
            </div>
        `;
        return;
    }

    displayedProducts.forEach(product => {
        const productItem = document.createElement('div');
        productItem.setAttribute('data-item', product.id)

        let productTags = '';
        if (product.new) {
            productTags += `<div class="product-tag text-button-uppercase bg-green px-3 py-0.5 inline-block rounded-full absolute top-3 left-3 z-[1]">New</div>`;
        }
        if (product.sale) {
            productTags += `<div class="product-tag text-button-uppercase text-white bg-red px-3 py-0.5 inline-block rounded-full absolute top-3 left-3 z-[1]">Sale</div>`;
        }

        let productImages = '';
        product.thumbImage.forEach((img, index) => {
            productImages += `<img key="${index}" class="w-full h-full object-cover duration-700" src="${img}" alt="img">`;
        });

        if (productContainer.classList.contains('style-grid')) {
            productItem.classList.add('product-item', 'grid-type');
            productItem.innerHTML = `
                    <div class="product-main cursor-pointer block" data-item="${product.id}">
                        <div class="product-thumb bg-white relative overflow-hidden rounded-2xl">
                            ${productTags}
                            <div class="list-action-right absolute top-3 right-3 max-lg:hidden">
                                <div
                                    class="add-wishlist-btn w-[32px] h-[32px] flex items-center justify-center rounded-full bg-white duration-300 relative">
                                    <div class="tag-action bg-black text-white caption2 px-1.5 py-0.5 rounded-sm">
                                        Add To Wishlist</div>
                                    <i class="ph ph-heart text-lg"></i>
                                </div>
                                <div
                                    class="compare-btn w-[32px] h-[32px] flex items-center justify-center rounded-full bg-white duration-300 relative mt-2">
                                    <div class="tag-action bg-black text-white caption2 px-1.5 py-0.5 rounded-sm">
                                        Compare Product</div>
                                    <i class="ph ph-arrow-counter-clockwise text-lg compare-icon"></i>
                                    <i class="ph ph-check-circle text-lg checked-icon"></i>
                                </div>
                            </div>
                            <div class="product-img w-full h-full aspect-[3/4]">
                                ${productImages}
                            </div>
                            <div class="list-action grid grid-cols-2 gap-3 px-5 absolute w-full bottom-5 max-lg:hidden">
                                <div
                                    class="quick-view-btn w-full text-button-uppercase py-2 text-center rounded-full duration-300 bg-white hover:bg-black hover:text-white">
                                    <span class="max-lg:hidden">Quick View</span>
                                    <i class="ph ph-eye lg:hidden text-xl"></i>
                                </div>
                                    ${product.action === 'add to cart' ? (
                    `
                                        <div
                                            class="add-cart-btn w-full text-button-uppercase py-2 text-center rounded-full duration-300 bg-white hover:bg-black hover:text-white"
                                            >
                                            <span class="max-lg:hidden">Add To Cart</span>
                                            <i class="ph ph-shopping-bag-open lg:hidden text-xl"></i>
                                        </div>
                                    `
                ) : (
                    `
                                        <div
                                            class="quick-shop-btn text-button-uppercase py-2 text-center rounded-full duration-500 bg-white hover:bg-black hover:text-white max-lg:hidden">
                                            Quick Shop</div>
                                        <div
                                            class="add-cart-btn w-full text-button-uppercase py-2 text-center rounded-full duration-300 bg-white hover:bg-black hover:text-white lg:hidden"
                                            >
                                            <span class="max-lg:hidden">Add To Cart</span>
                                            <i class="ph ph-shopping-bag-open lg:hidden text-xl"></i>
                                        </div>
                                        <div class="quick-shop-block absolute left-5 right-5 bg-white p-5 rounded-[20px]">
                                            <div class="list-size flex items-center justify-center flex-wrap gap-2">
                                                ${product.sizes && product.sizes.map((size, index) => (
                        `<div key="${index}" class="size-item w-10 h-10 rounded-full flex items-center justify-center text-button bg-white border border-line">${size.trim()}</div>`
                    )).join('')}
                                            </div >
                        <div class="add-cart-btn button-main w-full text-center rounded-full py-3 mt-4">Add
                            To cart</div>
                                                </div >
                        `
                )}
                                    </div>
                                </div>
                                <div class="product-infor mt-4 lg:mb-7">
                                    <div class="product-sold sm:pb-4 pb-2">
                                        <div class="progress bg-line h-1.5 w-full rounded-full overflow-hidden relative">
                                            <div class='progress-sold bg-red absolute left-0 top-0 h-full' style="width: ${Math.floor((product.sold / product.quantity) * 100)}%">
                                            </div>
                                        </div>
                                        <div class="flex items-center justify-between gap-3 gap-y-1 flex-wrap mt-2">
                                            <div class="text-button-uppercase">
                                                <span class='text-secondary2 max-sm:text-xs'>Sold:
                                                </span>
                                                <span class='max-sm:text-xs'>${product.sold}</span>
                                            </div>
                                            <div class="text-button-uppercase">
                                                <span class='text-secondary2 max-sm:text-xs'>Available:
                                                </span>
                                                <span class='max-sm:text-xs'>${product.quantity - product.sold}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="product-name text-title duration-300">${product.name}</div>
                                    ${product.variation.length > 0 && product.action === 'add to cart' ? (
                    `
                                            <div class="list-color py-2 max-md:hidden flex items-center gap-3 flex-wrap duration-500">
                                                ${product.variation.map((item, index) => (
                        `<div
                                                        key="${index}"
                                                        class="color-item w-8 h-8 rounded-full duration-300 relative"
                                                        style="background-color:${item.colorCode};"
                                                    >
                                                        <div class="tag-action bg-black text-white caption2 capitalize px-1.5 py-0.5 rounded-sm">${item.color}</div>
                                                    </div>
                                                    `
                    )).join('')}
                                            </div>`
                ) : (
                    `
                                        <div class="list-color list-color-image max-md:hidden flex items-center gap-3 flex-wrap duration-500">
                                            ${product.variation.map((item, index) => (
                        `
                                                <div
                                                    class="color-item w-12 h-12 rounded-xl duration-300 relative"
                                                    key="${index}"
                                                >
                                                    <img
                                                        src="${item.colorImage}"
                                                        alt='color'
                                                        class='rounded-xl w-full h-full object-cover'
                                                    />
                                                    <div class="tag-action bg-black text-white caption2 capitalize px-1.5 py-0.5 rounded-sm">${item.color}</div>
                                                </div>
                                            `
                    )).join('')}
                                        </div>
                                    `
                )}
                            <div
                            class="product-price-block flex items-center gap-2 flex-wrap mt-1 duration-300 relative z-[1]">
                            <div class="product-price text-title">$${product.price}.00</div>
                            ${Math.floor(100 - ((product.price / product.originPrice) * 100)) > 0 ? (
                    `
                                    <div class="product-origin-price caption1 text-secondary2">
                                        <del>$${product.originPrice}.00</del>
                                    </div>
                                    <div
                                        class="product-sale caption1 font-medium bg-green px-3 py-0.5 inline-block rounded-full">
                                        -${Math.floor(100 - ((product.price / product.originPrice) * 100))}%
                                    </div>
                            `
                ) : ('')}
                                </div>
                            </div>
                        </div>
                    </div>
                `
            productList.appendChild(productItem);
        }
        if (productContainer.classList.contains('style-list')) {
            productItem.classList.add('product-item', 'list-type');
            productItem.innerHTML = `
                    <div class="product-main cursor-pointer flex lg:items-center sm:justify-between gap-7 max-lg:gap-5">
                        <div class="product-thumb bg-white relative overflow-hidden rounded-2xl block max-sm:w-1/2">
                            ${productTags}
                            <div class="product-img w-full aspect-[3/4] rounded-2xl overflow-hidden">
                                ${productImages}
                            </div>
                            <div class="list-action px-5 absolute w-full bottom-5 max-lg:hidden">
                                <div class="quick-shop-block absolute left-5 right-5 bg-white p-5 rounded-[20px]">
                                        <div class="list-size flex items-center justify-center flex-wrap gap-2">
                                            ${product.sizes && product.sizes.map((size, index) => (
                `
                                                ${size === 'freesize' ? (
                    `
                                                    <div key="${index}" class="size-item px-3 py-1.5 rounded-full text-button bg-white border border-line">${size.trim()}</div>
                                                    `
                ) : (
                    `<div key="${index}" class="size-item w-10 h-10 rounded-full flex items-center justify-center text-button bg-white border border-line">${size.trim()}</div>`
                )}
                `
            )).join('')}
                                        </div>
                                        <div class="add-cart-btn button-main w-full text-center rounded-full py-3 mt-4">Add To cart</div>
                                </div>
                            </div>
                        </div>
                        <div class='flex sm:items-center gap-7 max-lg:gap-4 max-lg:flex-wrap lg:w-2/3 lg:flex-shrink-0 max-lg:w-full max-sm:flex-col max-sm:w-1/2'>
                                <div class="product-infor max-sm:w-full">
                                    <div class="product-name heading6 inline-block duration-300">${product.name}</div>
                                    <div class="product-price-block flex items-center gap-2 flex-wrap mt-2 duration-300 relative z-[1]">
                                        <div class="product-price text-title">$${product.price}.00</div>
                                        ${Math.floor(100 - ((product.price / product.originPrice) * 100)) > 0 ? (
                    `
                                                <div class="product-origin-price caption1 text-secondary2">
                                                    <del>$${product.originPrice}.00</del>
                                                </div>
                                                <div
                                                    class="product-sale caption1 font-medium bg-green px-3 py-0.5 inline-block rounded-full">
                                                    -${Math.floor(100 - ((product.price / product.originPrice) * 100))}%
                                                </div>
                                        `
                ) : ('')}
                                    </div>
                                    <div class='text-secondary desc mt-5 max-sm:hidden'>${product.description}</div>
                                </div>
                                <div class="action w-fit flex flex-col items-center justify-center">
                                    <div class="quick-shop-btn button-main whitespace-nowrap py-2 px-9 max-lg:px-5 rounded-full bg-white text-black border border-black hover:bg-black hover:text-white">
                                        Quick Shop
                                    </div>
                                    <div class="list-action-right flex items-center justify-center gap-3 mt-4">
                                        <div
                                            class="add-wishlist-btn w-[32px] h-[32px] flex items-center justify-center rounded-full bg-white duration-300 relative">
                                            <div class="tag-action bg-black text-white caption2 px-1.5 py-0.5 rounded-sm">
                                                Add To Wishlist</div>
                                                <i class="ph ph-heart text-lg"></i>
                                            </div>
                                        <div
                                            class="compare-btn w-[32px] h-[32px] flex items-center justify-center rounded-full bg-white duration-300 relative">
                                            <div class="tag-action bg-black text-white caption2 px-1.5 py-0.5 rounded-sm">
                                                Compare Product</div>
                                            <i class="ph ph-arrow-counter-clockwise text-lg compare-icon"></i>
                                            <i class="ph ph-check-circle text-lg checked-icon"></i>
                                        </div>
                                        <div
                                            class="quick-view-btn quick-view-btn-list w-[32px] h-[32px] flex items-center justify-center rounded-full bg-white duration-300 relative">
                                            <div class="tag-action bg-black text-white caption2 px-1.5 py-0.5 rounded-sm">
                                                Quick View</div>
                                            <i class="ph ph-eye text-lg"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                `
            productList.appendChild(productItem);
        }
    });
}

// Function to render pagination buttons
function renderPagination(products = []) {
    listPagination.innerHTML = '';
    const productsToDisplay = products.length ? products : productsData;

    let totalPages = Math.ceil(productsToDisplay.length / productsPerPage);
    const maxVisiblePages = 3;

    let startPage = 1;
    let endPage = totalPages;


    if (products.length > (productsPerPage * 2) && currentPage < 3) {
        startPage = 1;
        endPage = 3;
    }

    if (totalPages <= 2) {
        startPage = 1;
        endPage = 2;
    }

    if (products.length <= productsPerPage) {
        listPagination.remove()
    }

    if (currentPage > Math.floor(maxVisiblePages / 2)) {
        startPage = currentPage - Math.floor(maxVisiblePages / 2);
        endPage = startPage + maxVisiblePages - 1;
        if (endPage > totalPages) {
            endPage = totalPages;
            startPage = endPage - maxVisiblePages + 1;
        }
    }

    if (currentPage > 2) {
        const startButton = document.createElement('button');
        startButton.textContent = '<<';
        startButton.addEventListener('click', () => {
            currentPage = 1;
            renderProducts(currentPage, products);
            renderPagination(products);
            addEventToProductItem(products)
        });
        listPagination.appendChild(startButton);

        const prevButton = document.createElement('button');
        prevButton.textContent = '<';
        prevButton.addEventListener('click', () => {
            currentPage--;
            renderProducts(currentPage, products);
            renderPagination(products);
            addEventToProductItem(products)
        });
        listPagination.appendChild(prevButton);
    }

    for (let i = startPage; i <= endPage; i++) {
        if (i >= 1) {
            const button = document.createElement('button');
            button.textContent = i;

            if (i === currentPage) {
                button.classList.add('active');
            }

            button.addEventListener('click', () => {
                currentPage = i;
                renderProducts(currentPage, products);
                renderPagination(products);
                addEventToProductItem(products)
            });
            listPagination.appendChild(button);
        }
    }

    if (currentPage < totalPages - 1) {
        const nextButton = document.createElement('button');
        nextButton.textContent = '>';
        nextButton.addEventListener('click', () => {
            currentPage++;
            renderProducts(currentPage, products);
            renderPagination(products);
            addEventToProductItem(products)
        });
        listPagination.appendChild(nextButton);

        const endButton = document.createElement('button');
        endButton.textContent = '>>';
        endButton.addEventListener('click', () => {
            currentPage = totalPages;
            renderProducts(currentPage, products);
            renderPagination(products);
            addEventToProductItem(products)
        });
        listPagination.appendChild(endButton);
    }
}

// Initial fetch of products
if (productList) {
    fetchProducts();
}
