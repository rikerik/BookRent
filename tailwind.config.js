/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/static/**/*.{html,js,css}"],
  theme: {
    extend: {},
  },
  plugins: [require("rippleui")],
}