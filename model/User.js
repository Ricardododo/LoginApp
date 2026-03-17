const { db } = require('../database');

const User = {
    create(nombre, email, password) {
        return new Promise((resolve, reject) => {
            db.run(
                'INSERT INTO users (nombre, email, password) VALUES (?, ?, ?)',
                [nombre, email, password],
                function(err) {
                    if (err) reject(err);
                    resolve(this.lastID);
                }
            );
        });
    },

    findByEmail(email) {
        return new Promise((resolve, reject) => {
            db.get(
                'SELECT * FROM users WHERE email = ?',
                [email],
                (err, row) => {
                    if (err) reject(err);
                    resolve(row);
                }
            );
        });
    },

    findById(id) {
        return new Promise((resolve, reject) => {
            db.get(
                'SELECT id, nombre, email, created_at FROM users WHERE id = ?',
                [id],
                (err, row) => {
                    if (err) reject(err);
                    resolve(row);
                }
            );
        });
    },

    findAll() {
        return new Promise((resolve, reject) => {
            db.all(
                'SELECT id, nombre, email, created_at FROM users',
                (err, rows) => {
                    if (err) reject(err);
                    resolve(rows);
                }
            );
        });
    }
};

module.exports = User;
