package utils

func UnitTernaryOp(status bool, a, b uint) uint {
	if status {
		return a
	}

	return b
}
