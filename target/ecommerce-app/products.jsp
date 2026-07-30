<html>
<head>
<meta charset="UTF-8">

<style>
body {
    font-family: Arial;
    background: #f4f4f4;
}

h1 {
    text-align: center;
}

.container {
    display: flex;
    justify-content: center;
    gap: 30px;
    margin-top: 30px;
}

.card {
    background: white;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.2);
    width: 220px;
    text-align: center;
    transition: 0.3s;
}

.card:hover {
    transform: scale(1.05);
}

img {
    width: 180px;
    height: 150px;
    object-fit: cover;
    border-radius: 8px;
}

.price {
    color: green;
    font-weight: bold;
}

button {
    background: orange;
    border: none;
    padding: 10px;
    color: white;
    border-radius: 5px;
    cursor: pointer;
}
</style>
</head>

<body>

<h1>🛒 Product List</h1>

<div class="container">

    <div class="card">
        <img src="images/iphone.jpg">
        <h3>iPhone</h3>
        <p class="price">₹70,000</p>
        <button>Add to Cart</button>
    </div>

    <div class="card">
        <img src="images/laptop.jpg">
        <h3>Laptop</h3>
        <p class="price">₹50,000</p>
        <button>Add to Cart</button>
    </div>

</div>

</body>
</html>
