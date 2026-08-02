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
<div class="products-container">

    <div class="product-card">
        <img src="images/iphone.png" alt="iPhone">
        <h3>iPhone</h3>
        <p class="price">₹80000.0</p>

        <form action="add-to-cart" method="post">
            <input type="hidden" name="product" value="iPhone">
            <button>Add to Cart</button>
        </form>
    </div>

    <div class="product-card">
        <img src="images/laptop.png" alt="Laptop">
        <h3>Laptop</h3>
        <p class="price">₹60000.0</p>

        <form action="add-to-cart" method="post">
            <input type="hidden" name="product" value="Laptop">
            <button>Add to Cart</button>
        </form>
    </div>

</div>

</body>
</html>
