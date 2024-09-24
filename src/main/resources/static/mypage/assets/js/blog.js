// Table of contents
/**** Common ****/
/**** Function to fetch blogs from JSON file ****/
/**** Function to render blogs for a specific page ****/
/**** Function to render pagination buttons ****/
/**** Initial fetch of blogs ****/
/**** Filter blog by category ****/
/**** Blog Detail ****/


// Common
const blogContainer = document.querySelector('.blog');
const blogList = document.querySelector('.list-blog');
const listPagination = document.querySelector('.list-pagination');

let currentPage = 1;
const blogsPerPage = blogList ? Number(blogList.getAttribute('data-item')) : 3;
let blogsData = [];

// Function to fetch blogs from JSON file
function fetchBlogs() {
    fetch('./assets/data/Blog.json')
        .then(response => response.json())
        .then(data => {
            blogsData = data;
            renderBlogs(currentPage, blogsData);
            renderPagination(blogsData);
        })
        .catch(error => console.error('Error fetching blogs:', error));
}

// Function to render blogs for a specific page
function renderBlogs(page, blogs = []) {
    blogList.innerHTML = '';
    const blogsToDisplay = blogs.length ? blogs : blogsData;

    const startIndex = (page - 1) * blogsPerPage;
    const endIndex = startIndex + blogsPerPage;
    const displayedBlogs = blogsToDisplay.slice(startIndex, endIndex);

    displayedBlogs.forEach(blog => {
        const blogItem = document.createElement('div');

        if (blogContainer.classList.contains('default')) {
            blogItem.innerHTML = `
                <div class="blog-item style-default h-full cursor-pointer" data-item="${blog.id}">
                    <div class="blog-main h-full block pb-8 border-b border-line">
                        <div class="blog-thumb rounded-[20px] overflow-hidden">
                            <img
                                src=${blog.thumbImg}
                                alt='blog-img'
                                class='w-full duration-500'
                            />
                        </div>
                        <div class="blog-infor mt-7">
                            <div class="blog-tag bg-green py-1 px-2.5 rounded-full text-button-uppercase inline-block">${blog.tag}</div>
                            <div class="heading6 blog-title mt-3 duration-300">${blog.title}</div>
                            <div class="flex items-center gap-2 mt-2">
                                <div class="blog-author caption1 text-secondary">by ${blog.author}</div>
                                <span class='w-[20px] h-[1px] bg-black'></span>
                                <div class="blog-date caption1 text-secondary">${blog.date}</div>
                            </div>
                            <div class="body1 text-secondary mt-4">${blog.shortDesc}</div>
                            <div class="text-button underline mt-4">Read More</div>
                        </div>
                    </div>
                </div>
            `
            blogList.appendChild(blogItem);
        }
        else if (blogContainer.classList.contains('list')) {
            blogItem.innerHTML = `
                <div class="blog-item style-list h-full cursor-pointer" data-item="${blog.id}">
                    <div class="blog-main h-full flex items-center max-md:flex-col md:items-center gap-8 gap-y-5">
                        <div class="blog-thumb md:w-1/2 w-full rounded-[20px] overflow-hidden flex-shrink-0">
                            <img
                                src=${blog.thumbImg}
                                alt='blog-img'
                                class='w-full duration-500 flex-shrink-0'
                            />
                        </div>
                        <div class="blog-infor">
                            <div class="blog-tag bg-green py-1 px-2.5 rounded-full text-button-uppercase inline-block">${blog.tag}</div>
                            <div class="heading6 blog-title mt-3 duration-300">${blog.title}</div>
                            <div class="flex items-center gap-2 mt-2">
                                <div class="blog-author caption1 text-secondary">by ${blog.author}</div>
                                <span class='w-[20px] h-[1px] bg-black'></span>
                                <div class="blog-date caption1 text-secondary">${blog.date}</div>
                            </div>
                            <div class="body1 text-secondary mt-4">${blog.shortDesc}</div>
                            <div class="text-button underline mt-4">Read More</div>
                        </div>
                    </div>
                </div>
            `
            blogList.appendChild(blogItem);
        } else {
            blogItem.innerHTML = `
                <div class="blog-item style-one h-full cursor-pointer" data-item="${blog.id}">
                    <div class="blog-main h-full block">
                        <div class="blog-thumb rounded-[20px] overflow-hidden">
                            <img
                                src=${blog.thumbImg}
                                alt='blog-img'
                                class='w-full duration-500'
                            />
                        </div>
                        <div class="blog-infor mt-7">
                            <div class="blog-tag bg-green py-1 px-2.5 rounded-full text-button-uppercase inline-block">${blog.tag}</div>
                            <div class="heading6 blog-title mt-3 duration-300">${blog.title}</div>
                            <div class="flex items-center gap-2 mt-2">
                                <div class="blog-author caption1 text-secondary">by ${blog.author}</div>
                                <span class='w-[20px] h-[1px] bg-black'></span>
                                <div class="blog-date caption1 text-secondary">${blog.date}</div>
                            </div>
                        </div>
                    </div>
                </div>
            `
            blogList.appendChild(blogItem);
        }

        const blogItems = document.querySelectorAll('.blog-item')

        blogItems.forEach(blog => {
            // redirect to detail
            blog.addEventListener('click', () => {
                const blogId = blog.getAttribute('data-item')
                window.location.href = `blog-detail1.html?id=${blogId}`;
            })
        })
    });
}

// Function to render pagination buttons
function renderPagination(blogs = []) {
    listPagination.innerHTML = '';
    const blogsToDisplay = blogs.length ? blogs : blogsData;

    const totalPages = Math.ceil(blogsToDisplay.length / blogsPerPage);

    for (let i = 1; i <= totalPages; i++) {
        const button = document.createElement('button');
        button.textContent = i;

        if (i === 1) {
            button.classList.add('active')
        }

        button.addEventListener('click', () => {
            currentPage = i;
            if (listPagination.querySelector('.active')) {
                listPagination.querySelector('.active').classList.remove('active')
            }
            button.classList.add('active')
            renderBlogs(currentPage);
        });
        listPagination.appendChild(button);
    }
}

// Initial fetch of blogs
if (blogList) {
    fetchBlogs();
}


// Filter blog by category
const listCate = document.querySelector('.filter-category .list-cate')
const cateItems = document.querySelectorAll('.filter-category .list-cate .cate-item')

cateItems.forEach(item => {
    item.addEventListener('click', () => {
        if (listCate.querySelector('.active')) {
            listCate.querySelector('.active').classList.remove('active')
        }
        item.classList.add('active')

        const selectedCate = item.getAttribute('data-item')
        let blogFilter = blogsData.filter(blog =>
            blog.category === selectedCate
        )

        renderBlogs(1, blogFilter)
        renderPagination(blogFilter)
        if (blogFilter.length <= 3) {
            listPagination.remove()
        } else {
            if (listPagination.querySelector('.active')) {
                listPagination.querySelector('.active').classList.remove('active')
            }
            listPagination.querySelector('button').classList.add('active')
        }
    })
})


// Detail
const pathname = new URL(window.location.href)
const blogId = pathname.searchParams.get('id') === null ? '1' : pathname.searchParams.get('id')
const blogDetail = document.querySelector('.blog-detail')


if (blogDetail) {
    fetch('./assets/data/Blog.json')
        .then(response => response.json())
        .then(data => {
            const blogMain = data[Number(blogId) - 1]
    
            blogDetail.querySelector('.blog-img').setAttribute('src', blogMain.coverImg)
            blogDetail.querySelector('.blog-tag').innerHTML = blogMain.tag
            blogDetail.querySelector('.blog-title').innerHTML = blogMain.title
            blogDetail.querySelectorAll('.avatar img').forEach(img => {
                img.setAttribute('src', blogMain.avatar)
            })
            blogDetail.querySelectorAll('.blog-author').forEach(item => {
                item.innerHTML = blogMain.author
            })
            blogDetail.querySelector('.blog-date').innerHTML = blogMain.date
            blogDetail.querySelector('.blog-description').innerHTML = blogMain.description
    
            const listImg = blogDetail.querySelector('.list-img')
    
            blogMain.subImg.map(item => {
                const imgItem = document.createElement('div')
                imgItem.innerHTML = `
                    <img src=${item} alt='img' class='w-full rounded-3xl' />
                `
                listImg.appendChild(imgItem)
            })
    
            // Next, prev blog title
            const prevTitle = blogDetail.querySelector('.next-pre .left')
            const nextTitle = blogDetail.querySelector('.next-pre .right')
    
            if (blogId === '1') {
                prevTitle.querySelector('.prev').innerHTML = data[data.length - 1].title
                prevTitle.addEventListener('click', () => {
                    window.location.href = `blog-detail1.html?id=${data[data.length - 1].id}`;
                })
            } else {
                prevTitle.querySelector('.prev').innerHTML = data[Number(blogId) - 2].title
                prevTitle.addEventListener('click', () => {
                    window.location.href = `blog-detail1.html?id=${data[Number(blogId) - 2].id}`;
                })
            }
    
            if (Number(blogId) === data.length) {
                nextTitle.querySelector('.next').innerHTML = data[0].title
                nextTitle.addEventListener('click', () => {
                    window.location.href = `blog-detail1.html?id=1`;
                })
            } else {
                nextTitle.querySelector('.next').innerHTML = data[Number(blogId)].title
                nextTitle.addEventListener('click', () => {
                    window.location.href = `blog-detail1.html?id=${data[Number(blogId)].id}`;
                })
            }
        })
        .catch(error => console.error('Error fetching blogs:', error));
}
