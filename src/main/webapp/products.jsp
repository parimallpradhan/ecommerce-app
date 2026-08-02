<%@ page import="java.util.*" %>
<html>
<head>
<link rel="stylesheet" href="style.css">
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <h2>🛒 My Store</h2>
    <div>
        <a href="index.jsp">Home</a>
        <a href="cart">Cart</a>
    </div>
</div>

<!-- PRODUCTS GRID -->
<div class="products">

    <!-- PRODUCT 1 -->
    <div class="product-card">
        <img src="https://m.media-amazon.com/images/I/61bK6PMOC3L._SL1500_.jpg">
        <h3>iPhone 14</h3>
        <p class="price">₹79,999</p>
        <a class="add-btn" href="add-to-cart?id=1">Add to Cart</a>
    </div>

    <!-- PRODUCT 2 -->
    <div class="product-card">
        <img src="https://m.media-amazon.com/images/I/71d7rfSl0wL._SL1500_.jpg">
        <h3>Samsung Galaxy</h3>
        <p class="price">₹59,999</p>
        <a class="add-btn" href="add-to-cart?id=2">Add to Cart</a>
    </div>

    <!-- PRODUCT 3 -->
    <div class="product-card">
        <img src="https://m.media-amazon.com/images/I/61XO4bORHUL._SL1500_.jpg">
        <h3>OnePlus 11</h3>
        <p class="price">₹49,999</p>
        <a class="add-btn" href="add-to-cart?id=3">Add to Cart</a>
    </div>

</div>
<div class="container">
    <h2 class="section-title">Our Products</h2>

    <div class="product-grid">

        <div class="product-card">
            <img src="images/iphone.png" alt="iPhone">
            <h3>iPhone</h3>
            <p class="price">$999</p>

            <form action="add-to-cart" method="post">
                <input type="hidden" name="product" value="iPhone">
                <button>Add to Cart</button>
            </form>
        </div>

        <!-- Add more products same way -->

    </div>
</div>

</body>
</html>
