module.exports = {
    prefix: '',
    purge: {
      content: [
        './src/**/*.{html,ts}',
      ]
    },
    theme: {
      extend: {
        fontFamily: {
          sans: ['Lora', 'sans-serif'],
        },
      },
    },
    plugins: [require('@tailwindcss/forms'),require('@tailwindcss/typography')],
};
